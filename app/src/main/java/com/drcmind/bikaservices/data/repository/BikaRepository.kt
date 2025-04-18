package com.drcmind.bikaservices.data.repository

import com.drcmind.bikaservices.domain.model.User
import com.drcmind.bikaservices.utils.Result

interface BikaRepository {
    suspend fun setCurrentUser(user: User?) : Result<User?>

    suspend fun getCurrentUser() : Result<User?>
/*
    fun getCourses() : Flow<Result<List<Course>>>

    fun getCourseWithDetails(idCourse : String) : Flow<Result<CourseWithDetails>>
    suspend fun checkUserCourseReviewExists(courseId : String) : Result<Boolean>
    fun sendCourseReview(idCourse : String, rateOfCourse : Float, comment : String) : Flow<Result<List<Review>>>*/


}