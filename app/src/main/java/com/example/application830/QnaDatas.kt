package com.example.application830

import android.provider.BaseColumns

object QnaDatas {
    object qnaData : BaseColumns {  //  users 라는 DB 테이블의 데이터 컬럼 내용 정리
        const val TABLE_NAME = "qna"
        const val COLUMN_NAME_QUESTION = "question"
        const val COLUMN_NAME_ANSWER = "answer"
        const val COLUMN_NAME_QUESID = "QuesId"
        const val COLUMN_NAME_ANSWID = "AnswId"
    }
}