datatype term = Term of int * ((string * int) list);
datatype poly = Poly of term list;

val b1 = Poly ([Term (1,[("x",1),("y",1)]), Term(1,[("z",2)])]);
val b2 = Poly ([Term (1,[("z",2)]), Term (1,[("x",1),("y",2)])]);


fun sort less ls =
    let 
        fun ins (x,[]) = [x]
          | ins (x,y::ys) = 
            if less x y 
            then x :: y :: ys
            else y :: ins (x,ys)
        fun sort [] = []
          | sort (l::ls) = ins (l,sort ls)
    in 
        sort ls
    end;

    fun pairless ((a1,a2):(string*int)) (b1,b2) = (a1 < b1) orelse ((a1 = b1) andalso (a2 < b2))

fun pairequ (a1,a2) (b1,b2) = (a1=b1) andalso (a2=b2)

fun normterm (Term (i,ls)) = Term (i,sort pairless ls);

fun listequ f [] [] = true
  | listequ f (x::xs) [] = false
  | listequ f [] (y::ys) = false
  | listequ f (x::xs) (y::ys) =  (f x y) andalso (listequ f xs ys);

fun termequ (Term (x,xs)) (Term (y,ys)) = listequ pairequ xs ys;

fun polyequ (Poly []) (Poly []) = true
  | polyequ (Poly (x::xs)) (Poly ([])) = false
  | polyequ (Poly ([])) (Poly (y::ys)) = false 
  | polyequ (Poly (x::xs)) (Poly (y :: ys)) =
    (termequ x y) andalso (polyequ (Poly xs) (Poly ys))


(* *)

fun isMember i [] = false
  | isMember i (x::xs) = (i = x) orelse (isMember i xs);
fun exf_ (f,[],cs,ans) = ans
  | exf_ (f,l::ls,cs,ans) = 
    if ((isMember (f l) cs) andalso (not(isMember l ans)))
    then exf_ (f,ls,cs,l::ans)
    else exf_ (f,ls,cs,ans)

fun exf (f,l) = exf_ (f,l,l,[]);

