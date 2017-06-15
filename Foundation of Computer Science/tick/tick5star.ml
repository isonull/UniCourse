type color = int*int*int   (* RGB colour components, 0..255 *)
type xy = int*int       (* points (x, y) and sizes (w, h) *)
datatype image = Image of xy * color array array;

fun image ((x,y):xy) ((r,g,b):color) = Image((x,y),Array.tabulate(y,fn i => Array.tabulate(x,fn i => (r,g,b))));

fun size (Image(xy,arr)) = xy;

fun drawPixel (Image(xy,col)) ((r,g,b):color) ((x,y):xy) =
  Array.update(Array.sub(col,y),x,(r,g,b));

fun IntFormat i =
  if i>99 then (" " ^ Int.toString i)
  else if i>9 then ("  " ^ Int.toString i)
  else if i>=0 then ("   " ^ Int.toString i)
  else (Int.toString i);

fun RGBToString ((r,g,b):color) = (IntFormat r ^ IntFormat g ^ IntFormat b);

fun getRGB (Image(_,col)) x y = Array.sub(Array.sub(col,y),x);

fun toPPM (Image(xy,col)) filename =
  let
    val oc = TextIO.openOut filename
    val (w,h) = xy
    val x = ref 0
    val y = ref 0
  in
    TextIO.output(oc,"P3\n" ^ Int.toString w ^ " " ^ Int.toString h ^ "\n255\n");
    (* code to output image rows, one per line goes here *)
    while (!y) < h do(
      while (!x) < w do (
        TextIO.output(oc,RGBToString (getRGB (Image(xy,col)) (!x) (!y)));
        x := 1 + !x
      );
      TextIO.output(oc,"\n");
      y := 1 + !y;
      x := 0
    );
    TextIO.closeOut oc
  end;

fun drawHoriz (Image((x,y),col)) (RGB:color) ((x0,y0):xy) len =
  if x0 < x andalso y0 < y andalso len > 0 then(
    drawPixel (Image((x,y),col)) RGB (x0,y0);
    drawHoriz (Image((x,y),col)) (RGB:color) ((x0+1,y0):xy) (len-1);
    ()
  )
  else ()

fun drawVert (Image((x,y),col)) (RGB:color) ((x0,y0):xy) len =
  if x0 < x andalso y0 < y andalso len > 0 then(
  drawPixel (Image((x,y),col)) RGB (x0,y0);
  drawVert (Image((x,y),col)) (RGB:color) ((x0,y0+1):xy) (len-1);
  ()
  )
  else ()

fun drawDiag (Image((x,y),col)) (RGB:color) ((x0,y0):xy) len =
  if x0 < x andalso y0 < y andalso len > 0 then(
  drawPixel (Image((x,y),col)) RGB (x0,y0);
  drawDiag (Image((x,y),col)) (RGB:color) ((x0+1,y0+1):xy) (len-1);
  ()
  )
  else ()

fun drawLine (ima:image) (RGB:color) ((x0,y0):xy) ((x1,y1):xy) =
  let
    val dx = Int.abs (x1-x0)
    val dy = Int.abs (y1-y0)
    val sx = if x0 < x1 then 1 else ~1
    val sy = if y0 < y1 then 1 else ~1
    val e0 = dx - dy
    fun draw x y e =
      if x = x1 andalso y = y1 then
        drawPixel ima RGB (x,y)
      else(
        drawPixel ima RGB (x,y);
        let
          val e2 = 2 * e
          val x2 = x + (if e2 > ~dy then sx else 0)
          val y2 = y + (if e2 < dx  then sy else 0)
          val err2 = e + (if e2 > ~dy then ~dy else 0) + (if e2 < dx then dx else 0)
        in
          draw x2 y2 err2
        end
        )
  in
    draw x0 y0 e0
  end;

fun drawAll func (Image((x,y),cols)) =
  let
    fun drawrow f (Image((x,y),cols)) x0 yc = (
      drawPixel (Image((x,y),cols)) (f ((x0,yc):xy)) (x0,yc);
      if x0 < x - 1
      then drawrow f (Image((x,y),cols)) (x0 + 1) yc
      else ()
    )
    fun drawall f (Image((x,y),cols)) y0 = (
      drawrow f (Image((x,y),cols)) 0 y0;
      if y0 < y - 1
      then drawall f (Image((x,y),cols)) (y0 + 1)
      else ()
    )
  in
    drawall func (Image((x,y),cols)) 0
  end;

fun gradient ((x,y):xy) : color =
    (((x div 30) * 30) mod 256, 0, ((y div 30) * 30) mod 256);

fun gradImage () =  (*toPPM (drawAll gradient (image (640,480) (255,255,255))) "gradient.ppm";*)
  let
    val ima = image (640,480) (255,255,255);
  in
    drawAll gradient ima;
    toPPM ima "gradient.ppm"
  end;

fun mandelbrot maxIter (x,y) =
  let fun solve (a,b) c =
    if c = maxIter then 1.0
    else
      if (a*a + b*b <= 4.0) then
        solve (a*a - b*b + x,2.0*a*b + y) (c+1)
      else (real c)/(real maxIter)
  in
    solve (x,y) 0
  end;

fun chooseColour (n:real) : color=
  let
    val r = round ((Math.cos n) * 255.0)
    val g = round ((Math.cos n) * 255.0)
    val b = round ((Math.sin n) * 255.0)
  in
    (r,g,b)
  end;

fun rescale ((w,h):xy) (cx,cy,s) ((x,y):xy) =
  let
    val p = s * ((Real.fromInt x) / (Real.fromInt w) - 0.5) + cx
    val q = s * ((Real.fromInt y) / (Real.fromInt h) - 0.5) + cy
  in
   (p,q)
  end;

fun compute ((w,h):xy) (cx,cy,s) iter =
  let
    val ima = image (w,h) (255,255,255)
  in
    drawAll (fn (x,y) => (chooseColour (mandelbrot iter (rescale (w,h) (cx,cy,s) (x,y))))) ima;
    toPPM ima "mandelbrot.ppm"
  end;

(*compute (2000,2000) (~0.74364990,0.13188204,0.00073801) 200;*)
