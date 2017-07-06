delimiter //
-- vim: set expandtab tabstop=4 shiftwidth=4 autoindent smartindent:
--
-- Best.SQL
-- Reads a table constructed froma CSV HL7 file
-- and finds the hospital with the BEST OUTCOMES
-- by state - illness
--
-- This type of routine is implementing HL7 2.5
-- It has transformed the CSV data into a database,
-- but HAS NOT implemented the XML and related
-- frappery that goes along with HL7v3.
-- As such, these are good models on which to base
-- JSON RESTful transactions for the implementation
-- of HL7v4.
--
-- Mark Addinall - 2014
--
-- call Best("VIC", "HEART_ATTACK", @result);

drop procedure if exists `Best`//

create procedure Best(  IN  p_state     VARCHAR(255), 
                        IN  p_illness   VARCHAR(255),
                        OUT p_name      VARCHAR(255))
begin
    
    set @local_name = NULL;     -- firstly we need this as it seems mySQL
                                -- can only use @variables in a statement to
                                -- be prepared for execution.  Trying to use
                                -- the INOUT or OUT p_name just gives an
                                -- undefined variable error.
                                -- We NEED to set this to NULL on entry as
                                -- it is persistant over the life of the 
                                -- SESSION.  If the query fails, it retains the
                                -- value of the last successful execution!
                                -- EEEK!
    set @local_illness = NULL;

                                -- personally I would not have error
                                -- exceptions thrown in here but the
                                -- R guys wrote it in the specification
                                -- so in they go!
                                -- first test to see if we have been asked
                                -- about an existing valid state

    if ucase(p_state) not in (select State from states) then
        signal SQLSTATE '45001'
            set MESSAGE_TEXT = 'Invalid State';
    end if;
  
                                -- now test for a valid condition report

    select long_name from maladies where illness = ucase(p_illness)
        into @local_illness;

    if isnull(@local_illness) then
        signal SQLSTATE '45002'
            set MESSAGE_TEXT = 'Invalid condition';
    end if;

                                -- that's the end of throwing exceptions that are SOFT errors

    set @p_query = concat("select Hospital_Name into @local_name",  
        " from hospital_outcomes where ", @local_illness, " > 0 and State = '", p_state, 
            "' order by ", @local_illness, ", Hospital_Name ",   
            "Limit 1;");

    prepare best_query
        from @p_query;
    
    execute best_query;

    if isnull(@local_name) then     -- nothing found, no exception thrown, it is on the onus             
        set p_name = 'NA';          -- of the caller to test the result
    else
        set p_name = @local_name;
    end if;

end //

delimiter ;


