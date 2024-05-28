package com.example.admitcard

import com.google.gson.annotations.SerializedName


data class Model(
    @SerializedName("error")
    val error: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: List<DataItem>
)

data class DataItem(
    @SerializedName("id")
    val id: Int,

    @SerializedName("formNo")
    val formNo: Long,

    @SerializedName("formStatus")
    val formStatus: String,

    @SerializedName("admitId")
    val admitId: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("examDate")
    val examDate: String,

    @SerializedName("examTime")
    val examTime: String,

    @SerializedName("ps")
    val ps: String,

    @SerializedName("rollNo")
    val rollNo: String,

    @SerializedName("fstatus")
    val fstatus: String?, // Nullable field

    @SerializedName("subcode")
    val subcode: String?, // Nullable field

    @SerializedName("sname")
    val sname: String,

    @SerializedName("stuNameHindi")
    val stuNameHindi: String,

    @SerializedName("dob")
    val dob: String,

    @SerializedName("fname")
    val fname: String,

    @SerializedName("mname")
    val mname: String,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("genderCode")
    val genderCode: String?, // Nullable field

    @SerializedName("category")
    val category: String,

    @SerializedName("catCode")
    val catCode: String?, // Nullable field

    @SerializedName("guardianName")
    val guardianName: String,

    @SerializedName("aadhar")
    val aadhar: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("medium")
    val medium: String,

    @SerializedName("mobile")
    val mobile: String,

    @SerializedName("appDate")
    val appDate: String,

    @SerializedName("bloodGroup")
    val bloodGroup: String,

    @SerializedName("isCgDomicile")
    val isCgDomicile: String,

    @SerializedName("classRollNo")
    val classRollNo: String?, // Nullable field

    @SerializedName("collegeCode")
    val collegeCode: String?, // Nullable field

    @SerializedName("collegeName")
    val collegeName: String,

    @SerializedName("examCenter")
    val examCenter: String,

    @SerializedName("examCenterCode")
    val examCenterCode: String,

    @SerializedName("course")
    val course: String,

    @SerializedName("courseCode")
    val courseCode: String?, // Nullable field

    @SerializedName("enrollDate")
    val enrollDate: String?, // Nullable field

    @SerializedName("enrollmentNo")
    val enrollmentNo: String?, // Nullable field

    @SerializedName("facultyType")
    val facultyType: String,

    @SerializedName("highImage")
    val highImage: String,

    @SerializedName("higherImage")
    val higherImage: String,

    @SerializedName("isHc")
    val isHc: String,

    @SerializedName("isMinority")
    val isMinority: String,

    @SerializedName("nationality")
    val nationality: String?, // Nullable field

    @SerializedName("payCertDate")
    val payCertDate: String?, // Nullable field

    @SerializedName("payCertStatus")
    val payCertStatus: String?, // Nullable field

    @SerializedName("paymentAmount")
    val paymentAmount: String,

    @SerializedName("paymentDate")
    val paymentDate: String?, // Nullable field

    @SerializedName("paymentStatus")
    val paymentStatus: String?, // Nullable field

    @SerializedName("phone")
    val phone: String,

    @SerializedName("studentId")
    val studentId: String,

    @SerializedName("profileImage")
    val profileImage: String,

    @SerializedName("signatureImage")
    val signatureImage: String,

    @SerializedName("referanceTxnId")
    val referanceTxnId: String?, // Nullable field

    @SerializedName("religion")
    val religion: String?, // Nullable field

    @SerializedName("responseTxnId")
    val responseTxnId: String?, // Nullable field

    @SerializedName("semyr")
    val semyr: String,

    @SerializedName("session")
    val session: String?, // Nullable field

    @SerializedName("studentType")
    val studentType: String,

    @SerializedName("studyMedium")
    val studyMedium: String?, // Nullable field

    @SerializedName("isEditedAfterPay")
    val isEditedAfterPay: String?, // Nullable field

    @SerializedName("downloadCount")
    val downloadCount: Int,

    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("stuName")
    val stuName: String,

    @SerializedName("fatherName")
    val fatherName: String,

    @SerializedName("ugMarks")
    val ugMarks: String,

    @SerializedName("ugTotalMarks")
    val ugTotalMarks: String,

    @SerializedName("ugPercent")
    val ugPercent: String,

    @SerializedName("xmarks")
    val xmarks: String,

    @SerializedName("xpercent")
    val xpercent: String,

    @SerializedName("xresultStatus")
    val xresultStatus: String?, // Nullable field

    @SerializedName("xrollNo")
    val xrollNo: String,

    @SerializedName("xgroup")
    val xgroup: String?, // Nullable field

    @SerializedName("iboard")
    val iboard: String,

    @SerializedName("imarks")
    val imarks: String,

    @SerializedName("xtotalMarks")
    val xtotalMarks: String,

    @SerializedName("ipassingYear")
    val ipassingYear: String,

    @SerializedName("ipercent")
    val ipercent: String,

    @SerializedName("iresultStatus")
    val iresultStatus: String?, // Nullable field

    @SerializedName("irollNo")
    val irollNo: String,

    @SerializedName("xpassingYear")
    val xpassingYear: String,

    @SerializedName("xsubjects")
    val xsubjects: String?, // Nullable field

    @SerializedName("isubjects")
    val isubjects: String,

    @SerializedName("itotalMarks")
    val itotalMarks: String,

    @SerializedName("xboard")
    val xboard: String,

    @SerializedName("pstate")
    val pstate: String,

    @SerializedName("cdistrict")
    val cdistrict: String,

    @SerializedName("paddress")
    val paddress: String,

    @SerializedName("cstate")
    val cstate: String,

    @SerializedName("cpin")
    val cpin: String,

    @SerializedName("caddress")
    val caddress: String,

    @SerializedName("pdistrict")
    val pdistrict: String,

    @SerializedName("ppin")
    val ppin: String
    )
