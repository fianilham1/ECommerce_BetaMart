import React, { Component } from 'react';
import "./detail.css"
import profileImg from '../login/profile.svg';

class Detail extends Component {
    constructor(props) {
        super(props);
        this.state = {  }
    }

    renderAllowance = () => {
        const {detailUser} = this.props;
        if (detailUser.role==="Manager"){
            return ( 
            <div className="rowDetail"> 
                <div className="cellDetail">Allowance of Entertaint</div>
                <div className="cellDetail">: Rp{detailUser.allowance.entertaint}</div>
            </div>
            )
        }
        return <>
            <div className="rowDetail"> 
                <div className="cellDetail">Allowance of Food</div>
                <div className="cellDetail">: Rp{detailUser.allowance.food}</div>
            </div>
            <div className="rowDetail"> 
                <div className="cellDetail">Allowance of Transport</div>
                <div className="cellDetail">: Rp{detailUser.allowance.transport}</div>
            </div>
        </>
    }

    renderButton = () => {
        const {loggedUser, goToPage} = this.props;
        if(loggedUser.role==="Employee") return ''

        return (<button className="backButton" onClick={() => goToPage("userList")}> Back to List </button>)
    }

    render() { 
        const {detailUser} = this.props;
        console.log("DETAIL",detailUser.allowance)
        return ( 
            <>
            <div className="detailbg">
                <h2>Detail Salary For <span className="detailName">{detailUser.name}</span></h2>
                <h3>{detailUser.role}</h3>
                <img className="avatar" src={profileImg} alt=""/>
                <div className="detail-container">
                <div className="rowDetail"> 
                    <div className="cellDetail header">Details</div>
                </div>
                <div className="rowDetail"> 
                    <div className="cellDetail">Name</div>
                    <div className="cellDetail">: {detailUser.name}</div>
                </div>
                <div className="rowDetail"> 
                    <div className="cellDetail">Position</div>
                    <div className="cellDetail">: {detailUser.role}</div>
                </div>
                <div className="rowDetail"> 
                    <div className="cellDetail">Total Salary</div>
                    <div className="cellDetail">: Rp{detailUser.allowance.entertaint ? detailUser.mainSalary + detailUser.allowance.entertaint : detailUser.mainSalary + detailUser.allowance.food + detailUser.allowance.transport}</div>
                </div>
                <div className="rowDetail"> 
                    <div className="cellDetail">Main Salary</div>
                    <div className="cellDetail">: Rp{detailUser.mainSalary}</div>
                </div>
                {this.renderAllowance()}
                </div>
                {this.renderButton()}
            </div>
            </>
         );
    }
}
 
export default Detail;