import request from '@/utils/request'

/**
 * 获取所有转专业记录（带分页）
 * @param {Object} query 查询参数
 */
export function listTransfers(query) {
  return request({
    url: '/student/transfer/listAll',
    method: 'get',
    params: query
  })
}

/**
 * 根据ID获取转专业记录详情
 * @param {number} transferId 转专业ID
 */
export function getTransfer(transferId) {
  return request({
    url: '/student/transfer/detail',
    method: 'get',
    params: { transferId }
  })
}

/**
 * 添加转专业记录
 * @param {Object} data 转专业对象
 */
export function addTransfer(data) {
  return request({
    url: '/student/transfer/add',
    method: 'post',
    data: data
  })
}

/**
 * 更新转专业记录
 * @param {Object} data 转专业对象
 */
export function updateTransfer(data) {
  return request({
    url: '/student/transfer/update',
    method: 'post',
    data: data
  })
}

/**
 * 更新转专业记录审批状态
 * @param {Object} data 转专业对象
 */
export function updateApprovalStatus(data) {
  return request({
    url: '/student/transfer/updateApprovalStatus',
    method: 'post',
    data: data
  })
}

/**
 * 分配新学号
 * @param data
 * @returns {AxiosPromise}
 */
export function assignNewStudentNum(data) {
  return request({
    url: '/student/transfer/assignNewStudentNum',
    method: 'post',
    data: data
  })
}

/**
 * 删除转专业记录
 * @param {number} transferId 转专业ID
 */
export function deleteTransfer(transferId) {
  return request({
    url: '/student/transfer/delete',
    method: 'get',
    params: { transferId }
  })
}
