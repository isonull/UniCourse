datatype 'a stream = Nil
                   | Cons of 'a * (unit -> 'a stream)

fun map f (Cons (x,xs)) = Cons (f x, fn () => map f (xs ()));

fun merge (Nil,Nil) = Nil
  | merge (Nil,s) = s
  | merge (s,Nil) = s
  | merge (Cons (x,xs), Cons (y,ys)) = 
    if x < y 
    then Cons (x,fn () => merge (xs (),Cons (y,ys)))
    else if x > y 
         then Cons (y,fn () => merge (Cons (x,xs),ys ()))
         else Cons (x,fn () => merge (xs(),ys()))

fun time a b = a * b

fun pow23_ () = Cons (1, fn () => merge (map (time 2) (pow23_ ()), map (time 3) (pow23_ ())));

val pows23 = pow23_ ();

fun pow235_ () = Cons (1, fn () => merge (merge (map (time 2) (pow235_ ()), map (time 3) (pow235_ ()) ), map (time 5) (pow235_ ())) );

val pows235 = pow235_ ();
