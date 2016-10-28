datatype dictionary = Lf
                    | Br of (string * int) * dictionary * dictionary;
exception Collision;

fun insert (str,i) Lf = Br((str,i),Lf,Lf)
  | insert (str,i) (Br((x,y),b1,b2)) =
    if str = x 
    then raise Collision
    else if str < x 
         then Br((x,y), insert (str,i) b1, b2)
         else Br((x,y), b1, insert (str,i) b2);
exception Empty
fun findmax Lf = raise Empty
  | findmax (Br(x,b1,Lf)) = x
  | findmax (Br(x,b1,b2)) = findmax b2;

fun delmax Lf = raise Empty
  | delmax (Br(x,b1,Lf)) = b1
  | delmax (Br(x,b1,b2)) = delmax b2;

fun delete str Lf = raise Empty
  | delete str (Br((k,i),Lf,b2)) = 
    if str = k
    then b2
    else Br((k,i),Lf,delete str b2)
  | delete str (Br((k,i),b1,b2)) = 
    if str = k
    then Br(findmax b1,delmax b1,b2)
    else if str < k 
         then Br((k,i),delete str b1,b2)
         else Br((k,i),b1,delete str b2);

datatype 'a arr = Lf
                | Br of 'a * 'a arr * 'a arr;

fun tcon i Lf = Br(i,Lf,Lf)
  | tcon i (Br(x,b1,b2)) =  Br(i,tcon x b2, b1);

fun rtcon Lf = Lf
  | rtcon (Br(x,Lf,Lf)) = Lf
  | rtcon (Br(x,(Br(y,y1,y2)),b2)) = Br(y,b2,rtcon (Br(y,y1,y2)));


