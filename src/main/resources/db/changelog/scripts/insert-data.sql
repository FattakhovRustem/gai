DO $$
declare
	x text;
	y text;
	z text;
BEGIN
   FOREACH x IN array(select array['А', 'В', 'Е', 'К', 'М', 'Н', 'O', 'Р', 'С', 'Т', 'У', 'Х']::varchar[])
   LOOP
   		FOREACH y IN array(select array['А', 'В', 'Е', 'К', 'М', 'Н', 'O', 'Р', 'С', 'Т', 'У', 'Х']::varchar[])
   		LOOP
			FOREACH z IN array(select array['А', 'В', 'Е', 'К', 'М', 'Н', 'O', 'Р', 'С', 'Т', 'У', 'Х']::varchar[])
			LOOP
				FOR i IN 1..999
		        LOOP
   			        insert into numeric(word, numeric, issued, next)
			        values(x || y || z, i, false, false);
		        END LOOP;
   			END LOOP;
   		END LOOP;
   END LOOP;
END;
$$