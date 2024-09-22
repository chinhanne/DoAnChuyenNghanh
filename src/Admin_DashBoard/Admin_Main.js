import React, { Component } from 'react'
import Admin_Product from './Admin_Product'

export default class Admin_Main extends Component {
  render() {
    return (
      <div className="container">
        <div className="row">
            <div className="col-3"style={{backgroundColor:'orange'}}></div>
            <div className="col-9" style={{backgroundColor:'black'}}>
                <Admin_Product/>
            </div>
        </div>
      </div>
    )
  }
}
