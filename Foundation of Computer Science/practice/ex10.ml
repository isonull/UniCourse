datatype 'a tree = Lf
                 | Br of 'a * 'a tree * 'a tree;

datatype 'a queue = Que of 'a list * 'a list;

fun norm (Que ([],ys)) = Que (rev ys,[])
  | norm q = q;

fun qnull (Que ([],[])) = true
  | qnull _ = false;

fun enq (Que (xs,ys), x) = norm (Que (xs,x::ys));
fun deq (Que (x::xs,ys)) = norm (Que (xs,ys));
fun hdq (Que (x::xs,_)) = x;

fun breadth (Que ([],[])) = []
  | breadth (Que (Lf::xs,ys)) = breadth (Que (xs,ys))
  | breadth q =
        let 
            val Br (c,c1,c2) = hdq q
        in
            c :: breadth ( enq (enq (deq q, c1), c2) )
        end;

fun next n = [2*n,2*n+1];
