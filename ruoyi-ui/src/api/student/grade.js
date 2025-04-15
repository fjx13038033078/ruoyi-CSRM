import request from '@/utils/request'

// 获取所有学生成绩
export function listStudentGrades(query) {
  return request({
    url: '/student/grade/listAll',
    method: 'get',
    params: query
  })
}

// 获取单个成绩信息
export function getStudentGrade(gradeId) {
  return request({
    url: '/student/grade/detail',
    method: 'get',
    params: { gradeId }
  })
}

// 添加成绩
export function addStudentGrade(data) {
  return request({
    url: '/student/grade/add',
    method: 'post',
    data: data
  })
}

// 修改成绩
export function updateStudentGrade(data) {
  return request({
    url: '/student/grade/update',
    method: 'post',
    data: data
  })
}

// 删除成绩
export function deleteStudentGrade(gradeId) {
  return request({
    url: '/student/grade/delete',
    method: 'get',
    params: { gradeId }
  })
}
