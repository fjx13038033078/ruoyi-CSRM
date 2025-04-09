import request from '@/utils/request'

// 获取全部复查记录
export function listStudentReviews(query) {
  return request({
    url: '/student/review/listAll',
    method: 'get',
    params: query
  })
}

// 获取复查详情
export function getStudentReview(reviewId) {
  return request({
    url: '/student/review/detail',
    method: 'get',
    params: { reviewId }
  })
}

// 添加复查信息
export function addStudentReview(data) {
  return request({
    url: '/student/review/add',
    method: 'post',
    data: data
  })
}

// 更新复查信息
export function updateStudentReview(data) {
  return request({
    url: '/student/review/update',
    method: 'post',
    data: data
  })
}

// 删除复查信息
export function deleteStudentReview(reviewId) {
  return request({
    url: '/student/review/delete',
    method: 'get',
    params: { reviewId }
  })
}
