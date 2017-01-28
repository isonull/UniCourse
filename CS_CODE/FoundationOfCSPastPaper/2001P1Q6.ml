(*
2001 Paper 1 Question 6
Foundations of Computer Science
To represent the power series  ∞i=0 ai xi in a computer amounts to representing the coefficients a0, a1, a2, ... One possible representation is by a function of type int->real that returns the coefficient ai given i as an argument. An alternative representation is the following datatype:
datatype power = Cons of real * (unit -> power);
(a) Demonstrate the two representations by using each of them to implement these two power series:
(i) Theconstantpowerseriesc,witha0 =candai =0fori>0. [3marks]
(ii) The Taylor series  ∞i=0 xi/i! for the exponential function. [4 marks]
(b) Also implement (using both representations) each of the following operations on power series:
(i) Product with a scalar, given by c·  ∞i=0 ai xi  =  ∞i=0(cai) xi. [3 marks]
(ii) Sum, given by   ∞i=0 ai xi  +   ∞i=0 bi xi  =  ∞i=0(ai + bi) xi. [4 marks]
(iii) The product   ∞i=0 ai xi  ×   ∞i=0 bi xi , where the ith coefficient of the result is a0 bi +a1 bi−1 +···+ai b0. [6 marks]
You may assume there is an ML function real of type int->real that maps an integer to the equivalent real number.
*)
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
