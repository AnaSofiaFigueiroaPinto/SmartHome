import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import { addDevice } from '../services/devices.service';
import { useAsyncFn } from 'react-use';
import Alert from '@mui/material/Alert';
import Stack from '@mui/material/Stack';

export default function AddNewDevice({ open, roomName, handleClose }) {
    const [success, setSuccess] = React.useState(false);
  const [state, saveNewDevice] = useAsyncFn(async (payload) =>{
        try{
            const result= await addDevice(payload)
            setSuccess(true);
            setTimeout(() => {
                setSuccess(false);
                handleClose();
            }, 2000);
            return result;

        }catch(error){
            throw error;
        }
    }, [])
    console.log(state.error);

  return (
    <Dialog
      open={open}
      onClose={handleClose}
      PaperProps={{
        component: 'form',
        onSubmit: (event) => {
          event.preventDefault();
          const payload = { roomName, deviceName: event.target[0].value, model: event.target[1].value }
          saveNewDevice(payload);
        },
      }}
    >
      <DialogTitle>Add device - {roomName}</DialogTitle>
      <DialogContent>
        <DialogContentText>
          <p>Add a new device to the room `{roomName}`.</p>
          <p>The device will be added to the room and will be active by default.</p>
        </DialogContentText>
          {
              (state.loading === false && state.error != null)
              &&(
                  <Stack sx={{width: '100%'}} spacing={2}>
                      <Alert severity="error">{state.error?.response.data}</Alert>
                  </Stack>
              )}
          {success && (
              <Stack sx={{ width: '100%' }} spacing={2}>
                  <Alert severity="success">Device added successfully!</Alert>
              </Stack>
          )}
          <TextField
          autoFocus
          required
          margin="dense"
          id="name"
          name="name"
          label="Name"
          type="text"
          fullWidth
          variant="standard"
        />
        <TextField
          autoFocus
          required
          margin="dense"
          id="model"
          name="model"
          label="Model"
          type="model"
          fullWidth
          variant="standard"
        />

      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose} variant="contained" type="cancel" style={{ backgroundColor: '#336666', color: 'white' }}>
            Cancel
        </Button>
        <Button variant='contained' type="submit" style={{ backgroundColor: '#336666', color: 'white' }}>
            Submit
        </Button>
      </DialogActions>
    </Dialog>
  );
}