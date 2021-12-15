import ajax from "./ajax";

const BASE_URL = 'http://localhost:8082/supply'

//注册成为该图书的观察者
export const reqAddSupply = (account,id) => ajax(BASE_URL+'/addSupply',{account,id})
//
export const reqGetSupplyList = (page,pageSize)=>ajax(BASE_URL+'/getSupplyList',{page, pageSize})

export const reqModifyStock = (bookId,stock) =>ajax(BASE_URL+'/modifyStock',{bookId,stock})