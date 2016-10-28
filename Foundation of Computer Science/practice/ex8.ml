fun sw f x y = f y x;

fun pairless (x1,x2) (y1,y2) =
    (x1 < y1) orelse ((x1 = y1) andalso (x2 < y2));

fun insort less = 
    let fun ins(x,[]) = [x]
          | ins(x,y::ys) = 
              if (less x y) then x::y:: ys
              else y :: ins(x,ys);
        fun sort [] = []
          | sort (x::xs) = ins(x,sort(xs));
    in 
        sort
    end;

fun map f [] = []
  | map f (l::ls) = (f l) :: (map f ls);

fun map2 f ([]) _ = []
  | map2 f ([]::lss) st = (rev st) :: (map2 f lss [])
  | map2 f ((l::ls)::lss) st = map2 f (ls::lss) ((f l)::st) 

datatype 'a option = NONE
                   | SOME of 'a;

fun imap f [] = []
  | imap f ((SOME(t))::ls) = (SOME (f t)) :: (imap f ls)
  | imap f (NONE::ls) = NONE :: (imap f ls);

fun change (_,0) = [[]]
  | change ([],amt) = [] 
  | change (c::till,amt) = 
      if amt < c then change (till,amt)
      else
      (map (fn x => c :: x) (change(c::till, amt-c))) @ (change (till,amt));
