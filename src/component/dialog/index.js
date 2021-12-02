  
import React, { Component } from 'react';
import Dialog from "@material-ui/core/Dialog";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import Button from "@material-ui/core/Button";
import "./dialog.css"


class CustomDialog extends Component {
    constructor(props) {
        super(props);
        this.state = { 
            open:false
         }
    }

    handleClickToOpen = () => {
        this.setState({open:true})
      };
      
    handleToYes = e => {
        this.props.onClick(e); 
        this.setState({open:false})
      };

    handleToNo = e => {
        this.setState({open:false})
    };

    renderPage = () => {
        return (
            <>
            <Button className="submitButton" variant="outlined" color="primary" 
                    onClick={this.handleClickToOpen} >
                Submit
            </Button>
            <Dialog open={this.state.open} onClose={this.handleToClose}>
                <DialogTitle>{"Konfirmasi"}</DialogTitle>
                <DialogContent>
                <DialogContentText>
                   Apakah Anda Yakin Untuk Submit?
                </DialogContentText>
                </DialogContent>
                <DialogActions>
                <Button onClick={this.handleToYes} 
                        color="primary" autoFocus>
                    Ya
                </Button>
                <Button onClick={this.handleToNo}
                        color="primary" autoFocus>
                    Tidak
                </Button>
                </DialogActions>
            </Dialog>
            </>
        )
    }

    render() {
        
        return ( 
            <div stlye={{}}>
            {this.renderPage()}
            </div> 
         );
    }
}
 
export default CustomDialog;