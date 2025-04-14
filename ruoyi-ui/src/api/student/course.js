import request from '@/utils/request'

// 获取所有课程列表
export function listStudentCourses(query) {
  return request({
    url: '/student/course/listAll',
    method: 'get',
    params: query
  })
}

// 获取课程详情
export function getStudentCourse(courseId) {
  return request({
    url: '/student/course/detail',
    method: 'get',
    params: { courseId }
  })
}

// 添加课程
export function addStudentCourse(data) {
  return request({
    url: '/student/course/add',
    method: 'post',
    data: data
  })
}

// 更新课程
export function updateStudentCourse(data) {
  return request({
    url: '/student/course/update',
    method: 'post',
    data: data
  })
}

// 删除课程
export function deleteStudentCourse(courseId) {
  return request({
    url: '/student/course/delete',
    method: 'get',
    params: { courseId }
  })
}
