import ajax from "./ajax";

const BASE_URL = 'http://localhost:8082/message'

export const reqGetMessageList = (account)=>ajax(BASE_URL+'/getMessageList',{account})

export const reqDelMessage = (id) => ajax(BASE_URL+'/delMessage',{id})