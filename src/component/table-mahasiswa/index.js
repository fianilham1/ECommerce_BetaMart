import React, {Component} from 'react'
import './table-mahasiswa.css'
// import { Link } from "react-router-dom"
import { Redirect } from "react-router-dom"

class TableMahasiswa extends Component {
    constructor(props) {
        super(props);
        this.state = {
            submitNilaiStatus:false,
            nilaiDetail:false,
            profileDetail:false
        };
        this.baseState=this.state
    }

    componentWillMount(){
        this.resetStatus()
    }

    resetStatus = () => {
        this.setState(this.baseState)
    }

    handleSubmitNilai = e => {
        const {handleSubmitNilai} = this.props
        let id = e.target.id
        console.log("edit nilai id",id)   
        this.setState({
            submitNilaiStatus:true
        }, handleSubmitNilai(id))
    }

    handleDetail=(e)=>{
        const {handleDetail}=this.props
        let id = e.target.id
        let name = e.target.name
        console.log("id detail", id)
        this.setState({
            [name]:true
        },  handleDetail(id,name))
    }

    renderListMahasiswas = () => {
       
        if(this.state.submitNilaiStatus) return <Redirect to="submit-nilai-mahasiswa"/>

        if(this.state.nilaiDetail) return <Redirect to="detail-krs-mahasiswa"/>

        if(this.state.profileDetail) return <Redirect to="detail-profile-mahasiswa"/>

        const {dataMahasiswa} = this.props
        return dataMahasiswa.map((data, index) => {
            return (
                <tr key={index}>
                    <td className="cell num">{index + 1}</td>
                    <td className="cell">{data.nama}</td>
                    <td className="cell">{data.nim}</td>
                    <td className="cell">{data.jurusan}</td>
                    <td className="cell">{data.strata}</td>
                    <td className="cell">{data.semester}</td>
                    <td className="cell action">
                      
                    <button style={{borderRadius: 10, width: 100, height: 30,cursor: "pointer", marginRight: 0}} id={data.id} onClick={this.handleSubmitNilai}>Submit Nilai
                    </button>

                    <button name="nilaiDetail" style={{borderRadius: 10, width: 100, height: 30,cursor: "pointer", marginRight: 0}} id={data.id} onClick={this.handleDetail}>Detail KRS
                    </button>

                    <button name="profileDetail" style={{borderRadius: 10, width: 100, height: 30,cursor: "pointer", marginRight: 0}} id={data.id} onClick={this.handleDetail}>Detail Profile
                    </button>

                    </td>
                </tr>
            )
        })
    }

    render() {
        const {dataMahasiswa} = this.props
        console.log("data =", dataMahasiswa)
        return (
            <>
                {/*<div style={{marginLeft: 150}}>*/}
                {/*    <button style={{cursor: "pointer"}}>Add Nilai Mahasiswa</button>*/}
                {/*</div>*/}
                <h2 style={{marginLeft: 100}}>List Data Nilai Mahasiswa</h2>

                <table className="customers-list" width="80%">
                    <thead>
                        <tr>
                        <th>No</th>
                        <th>Nama</th>
                        <th>NIM</th>
                        <th>Jurusan</th>
                        <th>Strata</th>
                        <th>Semester</th>
                        <th>Actions</th>
                        </tr>
                    </thead>

                    <tbody>
                    {this.renderListMahasiswas()}
                    </tbody>
                </table>
            </>
        );
    }
}

export default TableMahasiswa;