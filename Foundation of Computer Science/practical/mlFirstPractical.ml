val g = fn x => 2*x;
val f = fn p => p 4;
f g;
[1,2,3];
fun q [] = []
  | q (a::cs) = (2*a) :: q cs;  

fun z (0,l) = l
  | z (n,l) = 1 :: z (n-1,l);
  
fun map (f,[]) = []
  | map (f,c::cs) = (f c) :: (map (f, cs));

fun filter (f,[]) = []
  | filter (f,c::cs) = if f c then c :: filter(f,cs)
                       else filter(f,cs);
fun foo (t,li) = 
        let 
            fun member (i,[]) = false
              | member (i,l::ls) = i = l orelse member (i,ls)
            fun foo1 ([]) = []
              | foo1 (c::cs) = if member (t c,li) then c :: foo1 (cs)
                                   else foo1(cs)    
        in
            foo1(li)
        end;
fun multfn x = fn y => y*(x+1);
fun multfn x y = y*(x+1);
multfn 1;
it 7;

(* fun fold f i L
 * L is [l1,l2...] 
 * t1 = f(i,l1)
 * ts = f(t1,l2)
 * t3= ...
 * ....*)

fun fold f i [] =  i
   | fold f i (l::ls) = fold f (f(i,l)) ls

fun tobin 0 = [0]
  | tobin n = (n mod 2) :: (tobin(n div 2));

fun  countones i = fold 
(fn (acc,bit) => acc+bit)
0
(tobin i);

datatype 'a stream = EOS
                   | CELL of 'a * (unit -> 'a stream);
           
CELL(10, fn () => CELL(9, fn () => EOS));

fun smap f EOS = EOS
  | smap f (CELL(i,g)) = CELL(f i, fn () => smap f ( g () ) );

fun sfilter p EOS = EOS
  | sfilter p (CELL(i,g)) = if (p i) then CELL (i,fn () => sfilter p (g()))
                            else sfilter p (g());

fun intsfrom n = CELL(n, fn () => intsfrom (n+1));

(* primes (intsfrom 2); *)
fun primes EOS = EOS
  | primes (CELL(i,g)) = CELL(i, fn () => primes( sfilter (fn x => x mod i <> 0) (g ()) ));     







                



