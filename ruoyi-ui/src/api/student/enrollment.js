import request from '@/utils/request'

// 获取全部录取信息
export function listEnrollments(query) {
  return request({
    url: '/student/enrollment/listAll',
    method: 'get',
    params: query
  })
}

// 获取单个录取信息详情
export function getEnrollment(enrollmentId) {
  return request({
    url: '/student/enrollment/detail',
    method: 'get',
    params: { enrollmentId }
  })
}

// 添加录取信息
export function addEnrollment(data) {
  return request({
    url: '/student/enrollment/add',
    method: 'post',
    data: data
  })
}

// 修改录取信息
export function updateEnrollment(data) {
  return request({
    url: '/student/enrollment/update',
    method: 'post',
    data: data
  })
}

//审核录取信息
export function updateEnrollmentStatus(data) {
  return request({
    url: '/student/enrollment/updateStatus',
    method: 'post',
    data: data
  })
}

// 删除录取信息
export function deleteEnrollment(enrollmentId) {
  return request({
    url: '/student/enrollment/delete',
    method: 'get',
    params: { enrollmentId }
  })
}
