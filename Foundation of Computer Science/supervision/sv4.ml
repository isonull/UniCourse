datatype 'a tree = Lf
                 | Br of 'a * ('a tree ref) * ('a tree ref);

fun lookup (x as ref Lf) a = false
  | lookup (x as ref (Br(v,l,f))) a = 
    (
        (v = a) orelse (lookup l a) orelse (lookup f a)
    );

fun update (x as ref Lf) a = 
    (x := Br(a,ref Lf, ref Lf) )
  | update (x as ref (Br(v,l,r))) a = 
    (
        if a < v then (update l a) else (update r a) 
    );

datatype mlist = Eol
               | Cell of int * (mlist ref);

fun isMember a [] = false
  | isMember a (l::ls) = (a=l) orelse (isMember a ls);

fun isCyclic (x as ref Eol) _ = false
  | isCyclic (x as ref (Cell(i,xs))) ls = 
    (
        (isMember x ls) orelse (isCyclic xs ((ref (Cell(i,xs)) )::ls))
    );
