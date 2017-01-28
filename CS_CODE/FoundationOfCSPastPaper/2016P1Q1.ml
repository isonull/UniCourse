(*
COMPUTER SCIENCE TRIPOS Part IA – 2016 – Paper 1 1 Foundations of Computer Science (LCP)
(a) Write brief notes on functions as values and results in ML, illustrated with the help of the functionals map and exists. What functions can we obtain from these via currying? [6 marks]
(b) Consider the function zarg defined below:
              fun zarg f ([], e) = e
              | zarg f (x::xs, e) = f(x, zarg f (xs,e));
Show that with the help of this function, it is possible to write an expression for the sum of a given list of integers. Then describe what zarg does in general.
[4 marks]
(c) A polymorphic type of branching trees can be declared as follows. Note that the children of a branch node are given as a list of trees, and that only the leaf nodes carry labels.
            datatype 'a vtree = Lf of 'a
                              | Br of ('a vtree) list;
(i) Write a function flat t that converts a given tree t of this type to a list of the labels (without eliminating duplicates). Your function should run in linear time in the size of the tree. [4 marks]
(ii) Write a function count x t that counts the number of times that x occurs as a label in t, but without first converting t to a list.
Note: Minimal credit will be given for solutions that use flat. [5 marks]
(iii) What is the type of count? [1 mark]
All ML code must be explained clearly and should be free of needless complexity.
*)

fun zarg f ([], e) = e
  | zarg f (x::xs, e) = f(x, zarg f (xs,e));

fun addList ls = zarg (fn (x,y) => x + y) (ls,0);

(*
Generally zarg take a function f which takes 2 variables and apply it to a list [a1,a2,a3...an] to return
f(a1,f(a2,f(a3...(f(an,e))))).
*)
datatype 'a vtree = Lf of 'a
                  | Br of ('a vtree) list;
fun flat (Lf(a)) = [a]
  | flat (Br([])) = []
  | flat (Br(x::xs)) = (flat x) @ (flat (Br(xs)));

fun countList x [] = 0
  | countList x (l::ls) =
    if x = l
    then 1 + countList x (l::ls)
    else countList x (l::ls);

fun count x t = countList x (flat t);

fun count2 x (Lf(a)) = if x = a then 1 else 0
  | count2 x (Br([])) = 0
  | count2 x (Br(p::ps)) =
    (count2 x p) + (count2 x Br(ps))
(*'a -> 'a vtree -> int*)
