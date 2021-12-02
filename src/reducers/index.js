  
import { combineReducers } from "redux"

import pageConfigReducer from "./pageConfig";
import paginationConfigReducer from "./paginationConfig"


export default combineReducers({
  
    pageConfig: pageConfigReducer,
    paginationConfig: paginationConfigReducer
})