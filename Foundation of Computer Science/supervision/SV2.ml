fun zero f x = x;
fun succ n f x = f (n f x);

val one = (succ zero);
val two = (succ (succ zero));
val three = (succ (succ (succ zero)));

fun toint n = n (fn x=>x+1) 0;

fun add m n = m succ n;
fun add1 m n f x = m f (n f x) ;

fun multi m n = m (add n) zero;
fun multi1 m n f x = m (n f) x;

fun power m n = n (multi m) (succ zero);
fun power1 m n f x = n (multi1 m) (succ zero) f x; 
 

