import React from 'react'
import './App.css'
import { category_common } from './requests';

function App() {

  React.useEffect(() => {
    category_common.get('')
    .then(r => console.log(r.data))
    .catch(console.warn);
  });

  return (
    <>
      <h1 className='text-center'>Hello world!</h1>
    </>
  );
}

export default App;
