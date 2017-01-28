(*
COMPUTER SCIENCE TRIPOS Part IA – 2015 – Paper 1 2 Foundations of Computer Science (LCP)
(a) Write brief notes on programming with lazy lists in ML. Your answer should
include the definition of a polymorphic type of infinite lazy lists, a function
to return the tail of a lazy list, a function to create the infinite list of all
positive integers, and an apply-to-all functional analogous to the list functional
map.
(b) Write a function diag that takes a lazy list of lazy lists,
[ [z11, z12, z13, . . .], [z21, z22, z23, . . .], [z31,z32,z33,...],...]
and returns the diagonal, namely the lazy list [z11, z22, z33, . . .].
[6 marks]
(∗) [3 marks]
(c) Write a function that takes two lazy lists [x1, x2, x3, . . .] and [y1, y2, y3, . . .]
and a function f of two arguments; it should return a lazy list of lazy lists like
(∗) above, with zij = f xi yj . [3 marks]
(d) Write a function that converts a lazy list of lazy lists like (∗) above to a
lazy list whose elements are all of the zij, enumerated in some order. [8 marks]
*)

datatype 'a seq = Nil
                | Cons of 'a * (unit -> 'a seq);

fun take (Cons(x,xs)) 1 = x
  | take (Cons(x,xs)) n = take (xs ()) (n-1);

fun diag (Cons(Cons(y,ys),xs)) =
  let
    fun a (Cons(t,ts)) n = Cons(take (take t n) n, fn () => a (Cons(t,ts)) (n+1))
  in
    a (Cons(Cons(y,ys),xs)) 1
  end;

fun aMap f x (Cons(y,ys)) = Cons(f x y, fn () => aMap f x (ys()));

fun bMap f (Cons(x,xs)) (Cons(y,ys)) =
  Cons(aMap f x (Cons(y,ys)), fn () => bMap f (xs()) (Cons(y,ys)));

fun trans (Cons(x,xs)) n i 1 = Cons(take (take x 1) i, fn () => trans (Cons(x,xs)) (n+1) 1 (n+1))
  | trans (Cons(x,xs)) n i j = Cons(take (take x j) i, fn () => trans (Cons(x,xs)) (n+1) (i+1) (j-1));
