    Expr -> Mul {"+" | "-" Mul}
     Mul -> Umin{"*" | "/" Umin}
    Umin -> ["-"] En
      En -> number | "("Expr")"