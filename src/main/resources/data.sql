insert into category (category_Id,name,tax_Value) values(101,'Category A',10);
insert into category (category_Id,name,tax_Value) values(102,'Category B',20);
insert into category (category_Id,name,tax_Value) values(103,'Category C',0);

insert into product (product_Id,product_Name,price,units_in_inventory,description,manufacturer)
values (1001,'Samsung S8',40000.50,5,'Samsung Mobile Phone','Samsung');
insert into product (product_Id,product_Name,price,units_in_inventory,description,manufacturer)
values (1002,'Phillips Iron',2450,20,'Phillips Iron for clothes','Phillips');
insert into product (product_Id,product_Name,price,units_in_inventory,description,manufacturer)
values (1003,'SkullCandy Earphones',1600.99,80,'SkullCAndy Earphones White','SkullCandy');

insert into product_category_xref (product_Category_Id,product_Id,category_Id) values(101,1001,102);
insert into product_category_xref (product_Category_Id,product_Id,category_Id) values(102,1003,103);
insert into product_category_xref (product_Category_Id,product_Id,category_Id) values(103,1002,101);

