(*
COMPUTER SCIENCE TRIPOS Part IA – 2015 – Paper 1 1 Foundations of Computer Science (LCP)
(a) Write brief notes about a tree representation of functional arrays, subscripted
by positive integers according to their representation in binary notation. How
efficient are the lookup and update operations? [6 marks]
(b) Write an ML function arrayoflist to convert the list [x1, . . . , xn] to the
corresponding functional array having xi at subscript position i for i = 1, . . . , n.
Your function should not call the update operation. [6 marks]
(c) Consider the task of finding out which elements of an array satisfy the predicate p,
returning the corresponding subscript positions as a list. For example, the list [2,3,6]
indicates that these three designated array elements, and no others, satisfy p.
Write an ML functional to do this for a given array and predicate, returning the
subscripts in increasing order. [8 marks]
All ML code must be explained clearly and should be free of needless complexity.
*)

datatype 'a array = Lf
                  | Br of 'a * 'a array * 'a array;

fun arrayoflist ls =
  let
    fun add Lf v 1 = Br(v,Lf,Lf)
      | add (Br(t,l,r)) v 1 = Br(v,l,r)
      | add Lf v n =
        if n mod 2 = 0
        then Br(0,add Lf v (n div 2),Lf)
        else Br(0,Lf,add Lf v (n div 2))
      | add (Br(t,l,r)) v n =
        if n mod 2 = 0
        then Br(t,add l v (n div 2),r)
        else Br(t,l,add r v (n div 2));

      fun all (Br(t,l,r)) [] n = Br(t,l,r)
        | all Lf [] n = Lf
        | all Lf (v::vs) n = all (add Lf v n) vs (n+1)
        | all (Br(t,l,r)) (v::vs) n = all (add (Br(t,l,r)) v n) vs (n+1);
  in
    all Lf ls 1
  end

exception outofrange
fun check Lf p n = raise outofrange
  | check (Br(t,l,r)) p 1 = p t
  | check (Br(t,l,r)) p n =
    if n mod 2 = 0
    then check l p (n div 2)
    else check r p (n div 2)

fun checkAll Lf p = []
  | checkAll (Br(t,l,r)) p =
    let
      fun a Lf p n = []
        | a (Br(t1,l1,r1)) p n =
        if check (Br(t1,l1,r1)) p n
        then n :: (a (Br(t1,l1,r1)) p (n+1))
        else a (Br(t1,l1,r1)) p (n+1)
        handle outofrange => []
    in
      rev (a (Br(t,l,r)) p 1)
    end
