select *
from mz.state_all
where part_state in (
-63, -- STATE_RACK
-64, -- LOAD_SIDE
-65, -- STATE_SECTOR
-67, -- TYPE_SHELF
-68  -- TYPE_RACK
)