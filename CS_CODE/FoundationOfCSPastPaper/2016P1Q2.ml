(*
COMPUTER SCIENCE TRIPOS Part IA – 2016 – Paper 1 2 Foundations of Computer Science (LCP)
(a) A prime number sieve is an algorithm for finding all prime numbers up to a
given limit n. The algorithm maintains a list, which initially holds the integers
 from 2 to n. The following step is then repeated: remove the head of this list,
 which will be a prime number, and remove all its multiples from the list. Write
 code for the algorithm above as an ML function of type int -> int list.
[4 marks]
(b) Consider the problem of eliminating all duplicates from a list of strings.
Write code for a function of type string list -> string list such that the output
contains the same elements as the input, possibly reordered, but where every
element occurs exactly once. The worst-case performance must be better than
quadratic in the length of the list. [6 marks]
(c) Consider the task of determining whether a given word (a string) can be
expressed by joining together various chunks (non-empty strings). If the chunks
are abra, cad and hal, then the word abracadabra can be expressed as abra|cad|abra.
Note that if the available chunks are ab, bra, cad and abra, then the first two
are no good for expressing abracadabra, and yet a solution can be found using
cad and abra. The chunks can be used any number of times and in any order.
Write code for a function that takes a list of chunks along with a word, and
returns a list of chunks that yield the word when concatenated. For the examples
above, the result should be ["abra", "cad", "abra"]. Exception Fail should be raised
if no solution exists. [10 marks]
Note: You are given a function delPrefix for removing an initial part of a string.
For example, delPrefix ("abra", "abracadabra") returns "cadabra", but delPrefix
("bra", "abracadabra") raises exception Fail.
All ML code must be explained clearly and should be free of needless complexity.
Well-known utility functions may be assumed to be available.
*)

fun listFilter f [] = []
  | listFilter f (l::ls) =
    if (f l)
    then l :: listFilter f ls
    else listFilter f ls;

fun dividable a b =
  if b mod a = 0
  then true
  else false;

fun generateList 0 = []
  | generateList 1 = []
  | generateList n = n :: generateList (n-1);

fun primeList n =
  let
    fun g [] qs = qs
      | g (p::ps) qs = g (listFilter (fn pp => not (dividable p pp)) ps) (p::qs)
  in
    g (rev (generateList n)) []
  end;

fun removeDup [] = []
  | removeDup (l::ls):(string list) = l :: listFilter (fn n => n <> l)  (removeDup ls);

fun listDelPre n [] = []
  | listDelPre 0 ls = ls
  | listDelPre n (l::ls) = listDelPre (n-1) ls;

(*should raise a exception if not isPrefix here*)
exception Fail;
fun delPrefix pre str =
  if String.isPrefix pre str
  then implode (listDelPre (size pre) (explode str))
  else raise Fail;

fun combineChunk _ "" ans pr ppr bk = rev ans
  | combineChunk [] str ans pr ppr bk = raise Fail
  | combineChunk cs str ans pr ppr bk =
    let
      val possPre = if bk then pr else listFilter (fn l => String.isPrefix l str) cs
    in
      if null possPre
      then
        if (null ppr) orelse (null ans)
        then raise Fail
        else combineChunk cs ((hd ans) ^ str) (tl ans) (listFilter (fn x => x <> (hd ans)) (hd ppr)) (tl ppr) true
      else combineChunk cs (delPrefix (hd possPre) str) ((hd possPre)::ans) [] (possPre::ppr) false
    end;

fun findComb cs str = combineChunk cs str [] [] [] false;
