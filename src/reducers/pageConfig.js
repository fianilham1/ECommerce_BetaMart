const defaultState = {
    currentPage : "/login"
}

const pageConfig = (state = defaultState, action) => {
    
    switch (action.type) {
        case "/login":
            return {
                currentPage:"/login"
            }
        case "/sign-up":
            return {
                currentPage:"/sign-up"
            }
        case "/dashboard":
            return {
                currentPage:"/dashboard"
            }
        case "/check-in-park":
            return {
                currentPage:"/check-in-park"
            }
        case "/list-parking":
            return {
                currentPage:"/list-parking"
            }
        default: 
            return state
    }
}

export default pageConfig