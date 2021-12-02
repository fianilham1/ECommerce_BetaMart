import './Todo.css';

import React, { Component } from 'react';

const prevPage = 'prev';
const nextPage = 'next';
let currentPage = 1;

const range = (from, to, step = 1) => {
    let i = from;
    const range = [];
  
    while (i <= to) {
      range.push(i);
      i += step;
    }
  
    return range;
  }

//event of pagination
const buildPagination = (entries,currentPage,dataLength) => {
    let pageTotal = Math.ceil(dataLength/entries); //total pagination number
    let showPagination = 3; //show n button only
    let diff = (showPagination-1); //differences between start index and end index
    let halfLengthPage = Math.floor(showPagination/2); //5/2 == 2
    
    console.log("TOTAL",pageTotal)
  
    if(diff>=pageTotal){
      showPagination = pageTotal;
      diff = (showPagination-1); //if custom showpagination >= page total based entries
      halfLengthPage = Math.floor(showPagination/2);
    }
  
    let startIndex = currentPage-halfLengthPage; //declare start index
    if(startIndex<1){
      startIndex = 1; //first page
    }
  
    let endIndex = startIndex+diff; //declare end index
    if(endIndex>pageTotal){
      endIndex = pageTotal;
    }
  
    let currentDiff = endIndex-startIndex;
    console.log("currentDIF",currentDiff)
    console.log("DIFF",diff)
    if(currentDiff<diff){
      let correction = diff-currentDiff;
      startIndex = currentPage - halfLengthPage - correction;
    }
  
    console.log("selish",endIndex-startIndex)
    console.log("current",currentPage)
    console.log("startpag",startIndex)
    console.log("endpag",endIndex)
    console.log("showpag",showPagination)
    console.log("half",halfLengthPage)
  
    let pages = range(startIndex,endIndex);

    //add previous button
    if(currentPage!==1){
        
        // if(currentPage >= diff && currentPage==startIndex){
        if(startIndex!==1 && showPagination!==pageTotal){
            pages = [1, prevPage, ...pages]; //pages = ['prev',1,2,3,4,5]
        }else{
            pages = [prevPage, ...pages];
        }
    }

    //add next button
    if(currentPage!==pageTotal){
        if(endIndex!==pageTotal && pageTotal!==0 && showPagination!==pageTotal){
            pages = [...pages, nextPage, pageTotal];
        }else{
            pages = [...pages, nextPage];
        }
    }
 
    return pages;    
  
    // pagination.innerHTML += buttonPagination;
    // let background = document.querySelector(".bg");
    // paginationBottom.innerHTML = pagination.innerHTML;
    // paginationBottom.style = pagination.style;
    // if(pageConfig.currentEntries==4){
    //   background.style.height = "720px";
    //   paginationBottom.style.top = "750px"; //entries : 4
    // }else{
    //   paginationBottom.style.top = "1180px"; //entries : 8
    //   background.style.height = "1150px";
    // }
    // console.log("clone",paginationBottom)
  }

class Pagination extends Component {
    constructor(props) {
        super(props);
        this.state = {  }
        // this.gotoPage = this.gotoPage.bind(this);
    }
    gotoPage = page => {
        this.props.onSelectPage(page); 
        console.log("PAGECLICKED",page)
    }
    handleClick = page => e => {
        e.preventDefault();
        this.gotoPage(page);
    }  
    handleMovePrev = e => {
        e.preventDefault();
        this.gotoPage(currentPage-1);
    }
    handleMoveNext = e => {
        e.preventDefault();
        this.gotoPage(currentPage+1);
    }
    render() { 
        const { pageConfig, userList } = this.props;
        const entries = pageConfig.currentEntries;
        const pageTotal = Math.ceil(userList.length/entries); //total pagination number
        currentPage = pageConfig.currentPage;
        const pages = buildPagination(entries,currentPage,userList.length)
        const showPages = pages.map((page, index) => {
            if (page === prevPage) return (
                <button key={index} href="#" onClick={this.handleMovePrev} className="prevNext">{'<<'}</button>
            );
            
            if (page === nextPage) return (
                <button key={index} href="#" onClick={this.handleMoveNext} className="prevNext">{'>>'}</button>
            );
    
            return (
                <button key={index} href="#" onClick={this.handleClick(page)} className={`page${ currentPage === page ? ' active' : ''}`}>{page}</button>
            );
        }) 
        console.log(showPages)
        return ( 
            <React.Fragment>
                <div className={`pagination_section${ currentPage === pageTotal ? ' maxPage' : ''}${ pages[0] === 1 && pages[1] === prevPage ? ' shift' : ''}`}>
                    {showPages}
                </div>
            </React.Fragment>
         );
    }
}
export default Pagination;