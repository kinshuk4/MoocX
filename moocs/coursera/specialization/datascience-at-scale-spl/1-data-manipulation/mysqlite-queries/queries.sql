.open reuters.db
select count(*) from (select * from Frequency where docid = '10398_txt_earn');
select count(*) from (select term from Frequency where docid = '10398_txt_earn' and count = 1);
select count(*) from (select term from Frequency where docid = '10398_txt_earn' and count = 1 UNION select term from Frequency where docid = '925_txt_trade' and count = 1);
select count(*) from (select DISTINCT docid from Frequency where term = 'law' OR term = 'legal');
select count(*) from (select docid,count(distinct term) as c from Frequency group by docid having c>300);
select count(*) from (select docid from Frequency where term= 'transactions' INTERSECT select docid from Frequency where term= 'world');

.open matrix.db
select a.row_num, b.col_num, sum(a.value*b.value) from a, b where a.col_num = b.row_num group by a.row_num, b.col_num;

.open reuters.db
select a.docid, b.docid, sum(a.count * b.count) from Frequency a , Frequency b on a.term = b.term where a.docid = '10080_txt_crude' and b.docid = '17035_txt_earn' group by a.docid, a.docid;

CREATE VIEW if not exists view_1 AS 
SELECT * FROM frequency 
UNION
SELECT 'q' as docid, 'washington' as term, 1 as count 
UNION
SELECT 'q' as docid, 'taxes' as term, 1 as count
UNION 
SELECT 'q' as docid, 'treasury' as term, 1 as count;

select A.docid, B.docid, sum(A.count * B.count) as similarity 
from view_1  A, view_1 B on A.term = B.term  
where A.docid = 'q' and B.docid != 'q'
group by A.docid, B.docid
order by similarity desc limit 10;
