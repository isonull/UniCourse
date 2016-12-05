datatype power = Cons of real * (unit -> power);

fun take (Cons(x,xs)) 0 = []
  | take (Cons(x,xs)) n = x :: (take (xs()) (n-1));

fun take_ (f:(real->real)) n = 
	if n < 1.5 andalso n > 0.5 
	then [f 0.0] 
	else (f (n-1.0)) :: take_ f (n-1.0); 

fun a1 (c:real) = Cons(c, fn () => a1 0.0);

fun a1_ n c = 
	if n < 0.5 andalso n > ~0.5 
	then c 
	else 0.0;

fun fact n = 
	if n < 1.0 
	then 1.0 
	else n * fact (n-1.0);

fun a2 n x = Cons(x, fn () => a2 (n+1.0) (x/(n+1.0)) );

fun a2_ n = 
	if n < 1.5 andalso n > ~0.5 
	then 1.0 
	else (a2_ (n-1.0)) / n ;

fun b1 (Cons(x,xs)) c = Cons(c*x,fn () => b1 (xs()) c);

fun b1_ (f : (real->real)) c = fn x => c * (f x);

fun b2 (Cons(x,xs)) (Cons(y,ys)) = Cons(x+y, fn () => b2 (xs()) (ys()));

fun b2_ (f1:(real->real)) (f2:(real->real)) = fn x => (f1 x) + (f2 x);

fun dotprod [] [] = 0.0
  | dotprod (x::xs) (y::ys) = x*y + (dotprod xs ys);

(*The time complexity can be inproved by adding two list arguements*)
fun b3 (Cons(x,xs)) (Cons(y,ys)) n = 
	Cons(dotprod (rev (take (Cons(y,ys)) (n+1))) (take (Cons(x,xs)) (n+1)), 
		fn () => b3 (Cons(x,xs)) (Cons(y,ys)) (n+1));

fun b3_ f1 f2 (n:real) = dotprod (rev (take_ f2 (n+1.0))) (take_ f1 (n+1.0));
