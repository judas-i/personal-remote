select * from (
select * from (
select 
  C.TABLE_NAME CTN,
  C.COLUMN_ID CID,
  C.COLUMN_NAME CCN,
  C.DATA_TYPE CDT,
  C.DATA_LENGTH CDL,
  C.DATA_PRECISION CDP,
  C.DATA_SCALE CDS,
  C.NULLABLE CNL,
  C.CHAR_LENGTH CCL,
  C.CHAR_USED CCU,
  C.ISPK CPK,
  NVL(P.TABLE_NAME,' ') PTN,
  P.COLUMN_ID PID,
  NVL(P.COLUMN_NAME,' ') PCN,
  P.DATA_TYPE PDT,
  P.DATA_LENGTH PDL,
  P.DATA_PRECISION PDP,
  P.DATA_SCALE PDS,
  P.NULLABLE PNL,
  P.CHAR_LENGTH PCL,
  P.CHAR_USED PCU,
  P.ISPK PPK
from 
  ( select TABLE_NAME,COLUMN_ID , COLUMN_NAME,DATA_TYPE,DATA_LENGTH, NVL(DATA_PRECISION,0) AS DATA_PRECISION ,NVL(DATA_SCALE,0) AS DATA_SCALE , ISPK ,NULLABLE,NVL(CHAR_LENGTH,0) AS CHAR_LENGTH ,NVL(CHAR_USED,' ') AS CHAR_USED from table_version A where version = ? and owner = ?) C ,
  ( select TABLE_NAME,COLUMN_ID , COLUMN_NAME,DATA_TYPE,DATA_LENGTH, NVL(DATA_PRECISION,0) AS DATA_PRECISION ,NVL(DATA_SCALE,0) AS DATA_SCALE , ISPK ,NULLABLE,NVL(CHAR_LENGTH,0) AS CHAR_LENGTH ,NVL(CHAR_USED,' ') AS CHAR_USED from table_version B where version = ? and owner = ?) P
WHERE 
  C.TABLE_NAME = P.TABLE_NAME(+)
AND
  C.COLUMN_NAME = P.COLUMN_NAME(+) 
)
WHERE 
  CTN || CCN || CDT || CDL || CDP || CDS || CID || CPK || CNL || CCU || CCL <> PTN || PCN || PDT || PDL || PDP || PDS || PID || PPK || PNL || PCU || PCL 
union 
select * from (
select 
  NVL(C.TABLE_NAME,' ') CTN,
  C.COLUMN_ID CID,
  NVL(C.COLUMN_NAME,' ') CCN,
  C.DATA_TYPE CDT,
  C.DATA_LENGTH CDL,
  C.DATA_PRECISION CDP,
  C.DATA_SCALE CDS,
  C.NULLABLE CNL,
  C.CHAR_LENGTH CCL,
  C.CHAR_USED CCU,
  C.ISPK CPK,
  P.TABLE_NAME PTN,
  P.COLUMN_ID PID,
  P.COLUMN_NAME PCN,
  P.DATA_TYPE PDT,
  P.DATA_LENGTH PDL,
  P.DATA_PRECISION PDP,
  P.DATA_SCALE PDS,
  P.NULLABLE PNL,
  P.CHAR_LENGTH PCL,
  P.CHAR_USED PCU,
  P.ISPK PPK
from 
  ( select TABLE_NAME,COLUMN_ID ,COLUMN_NAME,DATA_TYPE,DATA_LENGTH, NVL(DATA_PRECISION,0) AS DATA_PRECISION ,NVL(DATA_SCALE,0) AS DATA_SCALE , ISPK ,NULLABLE,NVL(CHAR_LENGTH,0) AS CHAR_LENGTH ,NVL(CHAR_USED,' ') AS CHAR_USED from table_version A where version = ? and owner = ?) C ,
  ( select TABLE_NAME,COLUMN_ID ,COLUMN_NAME,DATA_TYPE,DATA_LENGTH, NVL(DATA_PRECISION,0) AS DATA_PRECISION ,NVL(DATA_SCALE,0) AS DATA_SCALE , ISPK ,NULLABLE,NVL(CHAR_LENGTH,0) AS CHAR_LENGTH ,NVL(CHAR_USED,' ') AS CHAR_USED from table_version B where version = ? and owner = ?) P
WHERE 
  C.TABLE_NAME(+) = P.TABLE_NAME
AND
  C.COLUMN_NAME(+) = P.COLUMN_NAME
)
WHERE 
  CTN || CCN || CDT || CDL || CDP || CDS || CID || CPK || CNL || CCU || CCL <> PTN || PCN || PDT || PDL || PDP || PDS || PID || PPK || PNL || PCU || PCL 
)
ORDER BY 
  CTN ASC,
  CID ASC