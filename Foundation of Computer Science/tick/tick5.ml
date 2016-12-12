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
