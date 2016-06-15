function loadString(content){
  console.log('loadString: ' + content);
  document.getElementById('content').innerHTML = marked(content);
}