Insert
	into
	SI_CORE_DWH_SSI_ABSA.LITIGATION_BATCH_LOAD_TEST (AUD_BATCH_ID,
	AUD_IU_FLAG,
	AUD_SRC_SYS_CD,
	AUD_SRC_SYS_ID,
	AUD_SRC_SYS_NM,
	AUD_SRC_SYS_UPDT_VER_ID,
	AUD_SUB_BATCH_ID,
	LAST_UPDATE_DATE,
	LAST_UPDATE_USER,
	LINKED_MODULE_DESCRIPTION,
	LIT_ARRIVAL_DATE,
	LIT_BEARING_TYPE_CD,
	LIT_BEARING_TYPE_DESC,
	LIT_MODULE_CD,
	LIT_MODULE_DESC,
	LIT_STATE_CD,
	LIT_STATE_DESC,
	LIT_STATE_REASON_CD,
	LIT_STATE_REASON_DESC,
	LIT_STATUS_CD,
	LIT_STATUS_DESC,
	LIT_SUB_TYPE_CD,
	LIT_SUB_TYPE_DESC,
	LIT_TYPE_CD,
	LIT_TYPE_DESC,
	LITIGATION_ID,
	LITIGATION_NUM)
select AUD_BATCH_ID,
	AUD_IU_FLAG,
	AUD_SRC_SYS_CD,
	AUD_SRC_SYS_ID,
	AUD_SRC_SYS_NM,
	AUD_SRC_SYS_UPDT_VER_ID,
	AUD_SUB_BATCH_ID,
	LAST_UPDATE_DATE,
	LAST_UPDATE_USER,
	LINKED_MODULE_DESCRIPTION,
	LIT_ARRIVAL_DATE,
	LIT_BEARING_TYPE_CD,
	LIT_BEARING_TYPE_DESC,
	LIT_MODULE_CD,
	LIT_MODULE_DESC,
	LIT_STATE_CD,
	LIT_STATE_DESC,
	LIT_STATE_REASON_CD,
	LIT_STATE_REASON_DESC,
	LIT_STATUS_CD,
	LIT_STATUS_DESC,
	LIT_SUB_TYPE_CD,
	LIT_SUB_TYPE_DESC,
	LIT_TYPE_CD,
	LIT_TYPE_DESC,
	LITIGATION_ID,
	LITIGATION_NUM from (Select
	20 AS AUD_BATCH_ID,
	0 AS AUD_IU_FLAG,
	NULL AS AUD_SRC_SYS_CD,
	L_LITIGATION.ID AS AUD_SRC_SYS_ID,
	'IDIT' AS AUD_SRC_SYS_NM,
	L_LITIGATION.UPDATE_VERSION AS AUD_SRC_SYS_UPDT_VER_ID,
	8 AS AUD_SUB_BATCH_ID,
	L_LITIGATION.UPDATE_DATE AS LAST_UPDATE_DATE,
	USER_DETAILS.USER_ID AS LAST_UPDATE_USER,
	L_LITIGATION.LINKED_ENTITES_DESCRIPTION AS LINKED_MODULE_DESCRIPTION,
	L_LITIGATION.ARRIVAL_DATE AS LIT_ARRIVAL_DATE,
	L_LITIGATION.BEARING_TYPE_ID AS LIT_BEARING_TYPE_CD,
	MST_TABLE_VAL_LIST5.MST_VAL_SI_DESC AS LIT_BEARING_TYPE_DESC,
	L_LITIGATION.ENTITY_NR AS LIT_MODULE_CD,
	MST_TABLE_VAL_LIST.MST_VAL_SI_DESC AS LIT_MODULE_DESC,
	L_LITIGATION.STATE_ID AS LIT_STATE_CD,
	MST_TABLE_VAL_LIST6.MST_VAL_SI_DESC AS LIT_STATE_DESC,
	L_LITIGATION.STATE_REASON_ID AS LIT_STATE_REASON_CD,
	MST_TABLE_VAL_LIST7.MST_VAL_SI_DESC AS LIT_STATE_REASON_DESC,
	L_LITIGATION.STATUS AS LIT_STATUS_CD,
	MST_TABLE_VAL_LIST4.MST_VAL_SI_DESC AS LIT_STATUS_DESC,
	L_LITIGATION.SUB_TYPE AS LIT_SUB_TYPE_CD,
	MST_TABLE_VAL_LIST3.MST_VAL_SI_DESC AS LIT_SUB_TYPE_DESC,
	L_LITIGATION.TYPE AS LIT_TYPE_CD,
	MST_TABLE_VAL_LIST2.MST_VAL_SI_DESC AS LIT_TYPE_DESC,
	concat(L_LITIGATION.ID, '|', '101', '|', 'IDIT') AS LITIGATION_ID,
	L_LITIGATION.ID AS LITIGATION_NUM,
	ROW_NUMBER() OVER (ORDER BY L_LITIGATION.ID) rowNumber
FROM
	SI_IDIT_ODS.L_LITIGATION L_LITIGATION
LEFT JOIN SI_CORE_DWH_SSI_ABSA.USER_DETAILS USER_DETAILS ON
	L_LITIGATION.UPDATE_USER = USER_DETAILS.USER_NUM
LEFT JOIN SI_CORE_DWH_SSI_ABSA.MST_TABLE_VAL_LIST MST_TABLE_VAL_LIST5 ON
	L_LITIGATION.BEARING_TYPE_ID = MST_TABLE_VAL_LIST5.MST_TBL_VAL_NUM
	and MST_TABLE_VAL_LIST5.MST_TBL_NM_SHRT = 'MST_BEARING_TYPE'
LEFT JOIN SI_CORE_DWH_SSI_ABSA.MST_TABLE_VAL_LIST MST_TABLE_VAL_LIST ON
	L_LITIGATION.ENTITY_NR = MST_TABLE_VAL_LIST.MST_TBL_VAL_NUM
	and MST_TABLE_VAL_LIST.MST_TBL_NM_SHRT = 'MST_SYSTEM_ENTITY'
LEFT JOIN SI_CORE_DWH_SSI_ABSA.MST_TABLE_VAL_LIST MST_TABLE_VAL_LIST6 ON
	L_LITIGATION.STATE_ID = MST_TABLE_VAL_LIST6.MST_TBL_VAL_NUM
	and MST_TABLE_VAL_LIST6.MST_TBL_NM_SHRT = 'MST_AUTHORIZE_EXCEPTION_STATUS'
LEFT JOIN SI_CORE_DWH_SSI_ABSA.MST_TABLE_VAL_LIST MST_TABLE_VAL_LIST7 ON
	L_LITIGATION.STATE_REASON_ID = MST_TABLE_VAL_LIST7.MST_TBL_VAL_NUM
	and MST_TABLE_VAL_LIST7.MST_TBL_NM_SHRT = 'MST_AUTH_EX_STATUS_REASON'
LEFT JOIN SI_CORE_DWH_SSI_ABSA.MST_TABLE_VAL_LIST MST_TABLE_VAL_LIST4 ON
	L_LITIGATION.STATUS = MST_TABLE_VAL_LIST4.MST_TBL_VAL_NUM
	and MST_TABLE_VAL_LIST4.MST_TBL_NM_SHRT = 'MST_LITIGATION_STATUS'
LEFT JOIN SI_CORE_DWH_SSI_ABSA.MST_TABLE_VAL_LIST MST_TABLE_VAL_LIST3 ON
	L_LITIGATION.SUB_TYPE = MST_TABLE_VAL_LIST3.MST_TBL_VAL_NUM
	and MST_TABLE_VAL_LIST3.MST_TBL_NM_SHRT = 'MST_LITIGATION_SUB_TYPE'
LEFT JOIN SI_CORE_DWH_SSI_ABSA.MST_TABLE_VAL_LIST MST_TABLE_VAL_LIST2 ON
	L_LITIGATION.TYPE = MST_TABLE_VAL_LIST2.MST_TBL_VAL_NUM
	and MST_TABLE_VAL_LIST2.MST_TBL_NM_SHRT = 'MST_LITIGATION_TYPE')t
where  rowNumber between 5 and 10;