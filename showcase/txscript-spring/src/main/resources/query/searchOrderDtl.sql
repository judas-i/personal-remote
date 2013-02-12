SELECT 
		DTL.ITEM_CODE
	,	DTL.COUNT
	,	DTL.VERSION
FROM
		ORDER_DTL DTL
	,	ORD
WHERE
	ORD.ORDER_NO = DTL.ORDER_NO
--% if ($orderNo)
	AND
	ORD.ORDER_NO = :orderNo
--% end	