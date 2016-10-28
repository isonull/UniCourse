datatype 'a seq = Nil
                | Cons of 'a * (unit -> 'a seq);
fun map f Nil = Nil
  | map f (Cons (x,xs)) = Cons (f x, fn () => map f (xs()));

fun append Nil ys = ys
  | append (Cons(x,xs)) ys = Cons (x,fn () => append (xs ()) ys);

fun concat Nil = Nil
  | concat (Cons(x,xs)) = append x (concat (xs ()));

fun hd (Cons (x,xs)) = x
fun tl (Cons (x,xs)) = xs ();

fun change (cs,_,0,sq) = Cons(cs,sq)
  | change (cs,[],amt,sq) = sq ()
  | change (cs,t::ts,amt,sq) =
    if t>amt then change (cs,ts,amt,sq)
    else change (t::cs,t::ts,amt-t,fn () => change (cs,ts,amt,sq));

datatype 'a lazytree = Lf
                     | Br of 'a * (unit -> 'a lazytree) * (unit -> 'a lazytree)

fun preorder Lf = []
  | preorder (Br (x,x1,x2)) = x :: preorder (x1 ()) @ preorder (x2 ());

fun ipreorder (Lf,ans)  = ans
  | ipreorder (Br (x,x1,x2), ans) =
    x :: ipreorder (x1 () ,ipreorder (x2 (), ans));
