import request from '@/utils/request'

// 获取所有选课信息
export function listSelections(query) {
  return request({
    url: '/study/selection/listAll',
    method: 'get',
    params: query
  })
}

// 获取某条选课信息详情
export function getSelection(selectionId) {
  return request({
    url: '/study/selection/detail',
    method: 'get',
    params: { selectionId }
  })
}

// 添加选课
export function addSelection(data) {
  return request({
    url: '/study/selection/add',
    method: 'post',
    data: data
  })
}

// 更新选课
export function updateSelection(data) {
  return request({
    url: '/study/selection/update',
    method: 'post',
    data: data
  })
}

// 删除选课
export function deleteSelection(selectionId) {
  return request({
    url: '/study/selection/delete',
    method: 'get',
    params: { selectionId }
  })
}
