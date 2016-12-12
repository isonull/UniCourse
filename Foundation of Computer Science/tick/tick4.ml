fun nfold f 0 a = a
  | nfold f n a = f (nfold f (n-1) a);

fun sum a b = nfold (fn x => x+1) b (nfold (fn x => x+1) a 0)

fun product a b = nfold (sum a) b 0

fun power a b = nfold (product a) b 1;

datatype 'a stream = Cons of 'a * (unit -> 'a stream)
fun from k = Cons(k,fn () => from (k+1))

fun nth (Cons(x,xs),1) = x
  | nth (Cons(x,xs),n) = nth (xs (),n-1);

fun square_ k = Cons (k * k,fn () => square_ (k+1) );
val squares = square_ 1;
nth (squares,49);

fun map2 f (Cons (x,xs)) (Cons (y,ys)) = Cons (f x y,fn () => map2 f (xs ()) (ys ()));

