import React, {Component} from 'react';
import {Login, SignUp, ListParking, Dashboard, CheckInPark} from "../../page";
import { Switch, Route} from 'react-router-dom'
import { connect } from 'react-redux';

class Body extends Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            changeStatus:"getApiFirstTime",
        }
    }

    componentDidMount(){
        // if(this.state.changeStatus==="getApiFirstTime"){
        //     this.props.getApiUsers()
        //     this.setState({
        //         changeStatus:"waitingAnyReq"
        //     })
        // }
    }

    // componentDidUpdate(){
    //     if(this.state.changeStatus==="doEdit" || this.state.changeStatus==="doDelete" ||  this.state.changeStatus==="doAdd"){
    //         console.log("^^^SERVICE START -> Wait... before get users api again")
    //         setTimeout(() => { 
    //             this.props.getApiUsers()
    //             console.log("^^DONE -> get users api again to update")
    //             setTimeout(() => {  //delay re render userlist page
    //                 this.setState({
    //                     changeStatus:"waitingAnyReq"
    //                 })
    //             }, 7000);
    //         }, 8000);
    //     }
    // }

    // editUser = () => {
    //     this.setState({
    //         changeStatus:"doEdit"
    //     })
    // }

    // deleteUser = () => {
    //     this.setState({
    //         changeStatus:"doDelete"
    //     })
    // }

    // addNewUser = () => {
    //     this.setState({
    //         changeStatus:"doAdd"
    //     })
    // }

    //Code Above is Related to Api ^
    //////////////////////////////////////////////////
    //Code Below is Others 


    setDetailMhs = detailMhs => {
        this.setState({
            detailMhs: detailMhs
        })
    }

    setProfileDetailMhs = detailMhs => {
       
        let mhsObject = {
            nama:detailMhs.nama,
            nim:detailMhs.nim,
            ttl:detailMhs.ttl,
            gender:detailMhs.gender,
            mobile:detailMhs.mobile,
            email:detailMhs.email,
            alamat:detailMhs.alamat,
            tahun:detailMhs.tahun,
            jurusan:detailMhs.jurusan,
            strata:detailMhs.strata,
            foto:detailMhs.foto
        }
        
        this.setState({
            mhsProfileDetail: mhsObject
        })
    }

    addNewListPenerimaanHandler = newMahasiswa => {
        // const {goToPage} = this.props
        const listPenerimaan = this.state.listPenerimaan
        const listMhs = this.props.mhsList
        const idNew = listMhs.length===0 ? 1 : Math.max(...listMhs.map(mhs => mhs.id)) + 1
        console.log("IDCEK",idNew)
        const mhsObject = {
            ...newMahasiswa,
            id:idNew,
            nim:`210700${idNew}`
        }
        listPenerimaan.push(mhsObject) //save to temp list for penerimaan new mhs only
        this.setState({
            listPenerimaan
        })
        this.props.addNewMhs(mhsObject) //save to master mahasiswa list in redux

    }

    setEditMahasiswa = mhs => {
        this.setState({
            mhsEdit: mhs
        })
    }

    editlist = newData =>{
        this.setState({
            listSks:newData
        })
    }

    getDetailMhsLogged = () => {
        const listMhs = this.props.mhsList
        const idxMhs = listMhs.map(mhs => mhs.email).indexOf(this.props.userLogin.username)
        // console.log("CEK LOGGED MHS",idxMhs)
        return listMhs[idxMhs]
    }

   

    renderPage = () => {
        console.log("PROFILE SELECTED",this.state.mhsProfileDetail)
        const {userEdit} = this.state

        return (
        <Switch>
             <Route path="/" exact>
                <Login/>
            </Route>
            <Route path="/login">
                <Login/>
            </Route>
            <Route path="/sign-up">
                <SignUp />
            </Route>
            <Route path="/dashboard">
                <Dashboard />
            </Route>
            <Route path="/check-in-park">
                <CheckInPark />
            </Route>
            <Route path="/list-parking">
                <ListParking />
            </Route>
           
        </Switch>
        )
        
    }

    updateUsers = newUser => {
        console.log(newUser);

        if (newUser.id === "") {
            const oldUsers = this.state.users
            oldUsers.push({
                id: oldUsers.length ? Math.max(...oldUsers.map(user => user.id)) + 1 : 1,
                name: newUser.name,
                address: newUser.address
            })
            return this.setState({
                userList: oldUsers
            }, () => this.props.goToPage("list"))
        }

        const oldUsers = this.state.users
        const idxUser = oldUsers.map(user => user.id).indexOf(newUser.id)
        console.log(idxUser);
        oldUsers.splice(idxUser, 1, newUser)
        this.setState({
            userList: oldUsers
        }, () => this.props.goToPage("list"))
    }

    setUserEdit = userEdit => this.setState({userEdit}, () => this.props.goToPage("form"))

    clearUserEdit = () => this.setState({userEdit: {}})

    render() {
        
        return (
            this.renderPage()
        
        )
    }
}

// const mapStateToProps = state => ({
//     isLogedIn: state.Auth.statusLogin,
//     mhsList: state.MhsList.mahasiswas,
//     userLogin: state.Auth.userLogin,
//     loadingStatus: state.UserList.loadingStatus
// })

const mapDispatchToProps = dispatch => ({
    // addNewMhs: newMhs => dispatch({ type: "ADD_NEW", payload:{newMhs} }),
    // getApiUsers: () =>  dispatch({ type: "GETALLUSERS" })
})

export default connect(null, mapDispatchToProps)(Body);
