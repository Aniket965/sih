import React, { Component } from 'react'
import { Text, View } from 'react-native'
import firebase from 'firebase';


export default class Secure extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data:null
    }
  }

  decode(q) {

    fetch(`http://172.28.24.85:5000/?q=${q}`)
    .then((res)=> {
      console.log(res)
      // var config = {
      //   apiKey: "AIzaSyCOmubrc3gEd6LOW5UfRH5LVaL-GFgRCgk",
      //   authDomain: "not-so-awesome-project-45a2e.firebaseapp.com",
      //   databaseURL: "https://not-so-awesome-project-45a2e.firebaseio.com",
      //   projectId: "not-so-awesome-project-45a2e",
      //   storageBucket: "not-so-awesome-project-45a2e.appspot.com",
      //   messagingSenderId: "481329884022"
      // };
      // firebase.initializeApp(config);

      // var ref  = firebase.database().ref('/sensors')
      // var gov = firebase.database().ref('/gov')
      // ref.limitToLast(1).on('child_added',(snapshot)=>{
      //   let data =snapshot.val()['data'];
      //   gov.push({data})
      // })

    }).catch((err)=>console.log(err))


  }
  render() {
    return (
      <View style={{flex:1,justifyContent:"center",alignItems:"center"}} >

        <Text style={{fontSize:56,color:"white",fontWeight:"bold"}} >Success !! </Text>
        <Text style={{fontSize:0,color:"white",fontWeight:"bold"}} >{this.decode(this.props.data)}</Text>

      </View>
    )
  }
}