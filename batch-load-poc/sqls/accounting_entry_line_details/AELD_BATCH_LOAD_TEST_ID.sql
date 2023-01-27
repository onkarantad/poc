Insert
	into
	SI_CORE_DWH_SSI_ABSA.ACCOUNTING_ENTRY_LINE_DETAILS_BATCH_LOAD_TEST (ACCT_DET_ID,
	ACCT_DET_NUM,
	ACCT_ENTRY_HEADER_ID,
	ACCT_ENTRY_HEADER_NUM,
	ACCT_ENTRY_LINE_DET_ID,
	ACCT_ENTRY_LINE_DET_NUM,
	ACCT_ENTRY_LINE_ID,
	ACCT_HEADER_TYPE_CD,
	ACCT_HEADER_TYPE_DESC,
	AUD_BATCH_ID,
	AUD_IU_FLAG,
	AUD_SRC_SYS_CD,
	AUD_SRC_SYS_ID,
	AUD_SRC_SYS_NM,
	AUD_SRC_SYS_UPDT_VER_ID,
	AUD_SUB_BATCH_ID,
	CLAIM_ID,
	CLAIM_NUM,
	CONTRACT_COVERAGE_ID,
	CONTRACT_ID,
	CONTRACT_INSURED_ITEM_ID,
	CONTRACT_NUM,
	CONTRACT_VERSION_ID,
	CONTRACT_VERSION_NUM,
	CONTRIBUTION_ENTITY_ID,
	CONTRIBUTION_ENTITY_NUM,
	CONTRIBUTION_LAYER_NUM,
	CONTRIBUTOR_TYPE_CD,
	CONTRIBUTOR_TYPE_DESC,
	COVERAGE_EARNED_PREMIUM_ID,
	COVERAGE_EARNED_PREMIUM_NUM,
	COVERAGE_NUM,
	ENTRY_AMOUNT_HOME_CUR,
	ENTRY_AMOUNT_RATE,
	ENTRY_AMOUNT_TRAN_CUR,
	ENTRY_CURRENCY_CD,
	ENTRY_CURRENCY_DESC,
	ENTRY_DET_DUE_DATE,
	ENTRY_DET_STATUS_CD,
	ENTRY_DET_STATUS_DESC,
	ENTRY_DET_TYPE_CD,
	ENTRY_DET_TYPE_DESC,
	ENTRY_TRAN_DT,
	FUND_ID,
	FUND_NUM,
	LAST_UPDATE_DATE,
	LAST_UPDATE_USER,
	MONEY_VALUE_DT,
	OPP_ACCT_ENTRY_DET_ID,
	OPP_ACCT_ENTRY_DET_NUM,
	ORIG_ACCT_ENTRY_HEADER_ID,
	ORIG_ACCT_ENTRY_HEADER_NUM,
	ORIG_ACCT_ENTRY_LINE_DET_ID,
	ORIG_ACCT_ENTRY_LINE_DET_NUM,
	PAYMENT_COLLECTION_DT,
	PAYMENT_DISPERSAL_REF,
	PAYMENT_METHOD_CD,
	PAYMENT_METHOD_DESC,
	PAYMENT_SECONDARY_SOUR_DESC,
	PAYMENT_SECONDARY_SOUR_NUM,
	PAYMENT_SOURCE_CD,
	PAYMENT_SOURCE_DESC,
	PAYMENT_SOURCE_NUM,
	REFERENCE_TRANSACTION_ID,
	REFERENCE_TRANSACTION_NUM,
	SENT_TO_RI_INDICATOR,
	STORNO_ACCT_ENTRY_DET_ID,
	STORNO_ACCT_ENTRY_DET_NUM,
	SUPER_ENTRY_TYPE_CD,
	SUPER_ENTRY_TYPE_DESC,
	TRANSACTION_HIST_LINE_DET_ID,
	TRANSACTION_HIST_LINE_DET_NUM,
	TRANSACTION_ID,
	TRANSACTION_NUM)
Select
	ACCOUNTING_ENTRY_LINE.ACCT_DET_ID AS ACCT_DET_ID,
	ACCOUNTING_ENTRY_LINE.ACCT_DET_NUM AS ACCT_DET_NUM,
	ACCOUNTING_ENTRY_LINE.ACCT_ENTRY_HEADER_ID AS ACCT_ENTRY_HEADER_ID,
	ACCOUNTING_ENTRY_LINE.ACCT_ENTRY_HEADER_NUM AS ACCT_ENTRY_HEADER_NUM,
	concat(AC_ENTRY.ID, '|', '101', '|', 'IDIT') AS ACCT_ENTRY_LINE_DET_ID,
	AC_ENTRY.ID AS ACCT_ENTRY_LINE_DET_NUM,
	ACCOUNTING_ENTRY_LINE.ACCT_ENTRY_LINE_ID AS ACCT_ENTRY_LINE_ID,
	ACCOUNTING_ENTRY_LINE.ACCT_HEADER_TYPE_CD AS ACCT_HEADER_TYPE_CD,
	ACCOUNTING_ENTRY_LINE.ACCT_HEADER_TYPE_DESC AS ACCT_HEADER_TYPE_DESC,
	20 AS AUD_BATCH_ID,
	0 AS AUD_IU_FLAG,
	NULL AS AUD_SRC_SYS_CD,
	AC_ENTRY.ID AS AUD_SRC_SYS_ID,
	'IDIT' AS AUD_SRC_SYS_NM,
	AC_ENTRY.UPDATE_VERSION AS AUD_SRC_SYS_UPDT_VER_ID,
	6 AS AUD_SUB_BATCH_ID,
	CLAIMS_DETAILS.CLAIM_ID AS CLAIM_ID,
	CLAIMS_DETAILS.CLAIM_NUM AS CLAIM_NUM,
	GENINS_INS_ITM_SEC_CVG.CONTRACT_COVERAGE_ID AS CONTRACT_COVERAGE_ID,
	CONTRACT.CONTRACT_ID AS CONTRACT_ID,
	GENINS_CON_VER_LOB_INS_ITM.CONTRACT_INSURED_ITEM_ID AS CONTRACT_INSURED_ITEM_ID,
	CONTRACT.CONTRACT_NUM AS CONTRACT_NUM,
	CONTRACT_VERSION.CONTRACT_VERSION_ID AS CONTRACT_VERSION_ID,
	CONTRACT_VERSION.CONTRACT_VERSION_NUM AS CONTRACT_VERSION_NUM,
	NULL AS CONTRIBUTION_ENTITY_ID,
	NULL AS CONTRIBUTION_ENTITY_NUM,
	NULL AS CONTRIBUTION_LAYER_NUM,
	NULL AS CONTRIBUTOR_TYPE_CD,
	NULL AS CONTRIBUTOR_TYPE_DESC,
	NULL AS COVERAGE_EARNED_PREMIUM_ID,
	NULL AS COVERAGE_EARNED_PREMIUM_NUM,
	GENINS_INS_ITM_SEC_CVG.COVERAGE_NUM AS COVERAGE_NUM,
	AC_ENTRY.AMOUNT_SYSTEM AS ENTRY_AMOUNT_HOME_CUR,
	AC_ENTRY.AMOUNT_RATE AS ENTRY_AMOUNT_RATE,
	AC_ENTRY.AMOUNT AS ENTRY_AMOUNT_TRAN_CUR,
	MST_TABLE_VAL_LIST4.MST_VAL_SI_CD AS ENTRY_CURRENCY_CD,
	MST_TABLE_VAL_LIST4.MST_VAL_LONG_DESC AS ENTRY_CURRENCY_DESC,
	AC_ENTRY.DUE_DATE AS ENTRY_DET_DUE_DATE,
	MST_TABLE_VAL_LIST3.MST_VAL_SI_CD AS ENTRY_DET_STATUS_CD,
	MST_TABLE_VAL_LIST3.MST_VAL_LONG_DESC AS ENTRY_DET_STATUS_DESC,
	MST_TABLE_VAL_LIST.MST_VAL_SI_CD AS ENTRY_DET_TYPE_CD,
	MST_TABLE_VAL_LIST.MST_VAL_SI_DESC AS ENTRY_DET_TYPE_DESC,
	NULL AS ENTRY_TRAN_DT,
	NULL AS FUND_ID,
	NULL AS FUND_NUM,
	AC_ENTRY.UPDATE_DATE AS LAST_UPDATE_DATE,
	USER_DETAILS.USER_ID AS LAST_UPDATE_USER,
	NULL AS MONEY_VALUE_DT,
	NULL AS OPP_ACCT_ENTRY_DET_ID,
	NULL AS OPP_ACCT_ENTRY_DET_NUM,
	ACCOUNTING_ENTRY_LINE1.ACCT_ENTRY_HEADER_ID AS ORIG_ACCT_ENTRY_HEADER_ID,
	ACCOUNTING_ENTRY_LINE1.ACCT_ENTRY_HEADER_NUM AS ORIG_ACCT_ENTRY_HEADER_NUM,
	NULL AS ORIG_ACCT_ENTRY_LINE_DET_ID,
	NULL AS ORIG_ACCT_ENTRY_LINE_DET_NUM,
	NULL AS PAYMENT_COLLECTION_DT,
	NULL AS PAYMENT_DISPERSAL_REF,
	NULL AS PAYMENT_METHOD_CD,
	NULL AS PAYMENT_METHOD_DESC,
	NULL AS PAYMENT_SECONDARY_SOUR_DESC,
	NULL AS PAYMENT_SECONDARY_SOUR_NUM,
	NULL AS PAYMENT_SOURCE_CD,
	NULL AS PAYMENT_SOURCE_DESC,
	NULL AS PAYMENT_SOURCE_NUM,
	TRANSACTION_DETAILS.TRANSACTION_ID AS REFERENCE_TRANSACTION_ID,
	TRANSACTION_DETAILS.TRANSACTION_NUM AS REFERENCE_TRANSACTION_NUM,
	NULL AS SENT_TO_RI_INDICATOR,
	NULL AS STORNO_ACCT_ENTRY_DET_ID,
	NULL AS STORNO_ACCT_ENTRY_DET_NUM,
	MST_TABLE_VAL_LIST2.MST_VAL_SI_CD AS SUPER_ENTRY_TYPE_CD,
	MST_TABLE_VAL_LIST2.MST_VAL_LONG_DESC AS SUPER_ENTRY_TYPE_DESC,
	TRANSACTION_HIST_LINE_DETAILS.TRANSACTION_HIST_LINE_ID AS TRANSACTION_HIST_LINE_DET_ID,
	TRANSACTION_HIST_LINE_DETAILS.TRANSACTION_HIST_LINE_NUM AS TRANSACTION_HIST_LINE_DET_NUM,
	TRANSACTION_HIST_LINE_DETAILS.TRANSACTION_ID AS TRANSACTION_ID,
	TRANSACTION_HIST_LINE_DETAILS.TRANSACTION_NUM AS TRANSACTION_NUM
FROM
	SI_IDIT_ODS.AC_ENTRY AC_ENTRY
INNER JOIN SI_CORE_DWH_SSI_ABSA.ACCOUNTING_ENTRY_LINE ACCOUNTING_ENTRY_LINE ON
	AC_ENTRY.TRANSACTION_ID = ACCOUNTING_ENTRY_LINE.ACCT_ENTRY_HEADER_NUM
	AND AC_ENTRY.ACCOUNT_ID = ACCOUNTING_ENTRY_LINE.ACCT_DET_NUM
LEFT JOIN SI_CORE_DWH_SSI_ABSA.CLAIMS_DETAILS CLAIMS_DETAILS ON
	AC_ENTRY.CLAIM_ID = CLAIMS_DETAILS.CLAIM_NUM
LEFT JOIN SI_CORE_DWH_SSI_ABSA.GENINS_INS_ITM_SEC_CVG GENINS_INS_ITM_SEC_CVG ON
	AC_ENTRY.COVER_ID = GENINS_INS_ITM_SEC_CVG.COVERAGE_NUM
LEFT JOIN SI_CORE_DWH_SSI_ABSA.CONTRACT CONTRACT ON
	AC_ENTRY.POLICY_HEADER_ID = CONTRACT.CONTRACT_NUM
LEFT JOIN SI_CORE_DWH_SSI_ABSA.GENINS_CON_VER_LOB_INS_ITM GENINS_CON_VER_LOB_INS_ITM ON
	AC_ENTRY.ORIGINAL_LOB_ASSET_ID = GENINS_CON_VER_LOB_INS_ITM.INSURED_ITEM_NUM
LEFT JOIN SI_CORE_DWH_SSI_ABSA.CONTRACT_VERSION CONTRACT_VERSION ON
	AC_ENTRY.POLICY_ID = CONTRACT_VERSION.CONTRACT_VERSION_NUM
LEFT JOIN SI_CORE_DWH_SSI_ABSA.MST_TABLE_VAL_LIST MST_TABLE_VAL_LIST4 ON
	AC_ENTRY.CURRENCY_ID = MST_TABLE_VAL_LIST4.MST_TBL_VAL_NUM
	and MST_TABLE_VAL_LIST4.MST_TBL_NM_SHRT = 'MST_CURRENCY'
LEFT JOIN SI_CORE_DWH_SSI_ABSA.MST_TABLE_VAL_LIST MST_TABLE_VAL_LIST3 ON
	AC_ENTRY.ENTRY_STATUS = MST_TABLE_VAL_LIST3.MST_TBL_VAL_NUM
	and MST_TABLE_VAL_LIST3.MST_TBL_NM_SHRT = 'MST_ENTRY_STATUS'
LEFT JOIN SI_CORE_DWH_SSI_ABSA.MST_TABLE_VAL_LIST MST_TABLE_VAL_LIST ON
	AC_ENTRY.ENTRY_TYPE = MST_TABLE_VAL_LIST.MST_TBL_VAL_NUM
	and MST_TABLE_VAL_LIST.MST_TBL_NM_SHRT = 'MST_ENTRY_TYPE'
LEFT JOIN SI_CORE_DWH_SSI_ABSA.USER_DETAILS USER_DETAILS ON
	AC_ENTRY.UPDATE_USER = USER_DETAILS.USER_NUM
LEFT JOIN SI_CORE_DWH_SSI_ABSA.ACCOUNTING_ENTRY_LINE ACCOUNTING_ENTRY_LINE1 ON
	AC_ENTRY.ORIGINAL_TRANSACTION_ID = ACCOUNTING_ENTRY_LINE1.ACCT_ENTRY_HEADER_NUM
	AND AC_ENTRY.ACCOUNT_ID = ACCOUNTING_ENTRY_LINE1.ACCT_DET_NUM
LEFT JOIN SI_CORE_DWH_SSI_ABSA.TRANSACTION_DETAILS TRANSACTION_DETAILS ON
	AC_ENTRY.REFERENCE_ID = TRANSACTION_DETAILS.TRANSACTION_NUM
LEFT JOIN SI_CORE_DWH_SSI_ABSA.MST_TABLE_VAL_LIST MST_TABLE_VAL_LIST2 ON
	AC_ENTRY.SUPER_ENTRY_TYPE = MST_TABLE_VAL_LIST2.MST_TBL_VAL_NUM
	and MST_TABLE_VAL_LIST2.MST_TBL_NM_SHRT = 'MST_SUPER_ENTRY_TYPE'
LEFT JOIN SI_CORE_DWH_SSI_ABSA.TRANSACTION_HIST_LINE_DETAILS TRANSACTION_HIST_LINE_DETAILS ON
	AC_ENTRY.PAID_BY_INSTALLMENT_DETAILS_ID = TRANSACTION_HIST_LINE_DETAILS.TRANSACTION_HIST_LINE_NUM
WHERE AC_ENTRY.ID >  '?business_start_date'
    		AND AC_ENTRY.ID <= '?business_end_date';