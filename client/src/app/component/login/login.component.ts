import { Component } from '@angular/core';
import { IntergrationService } from '../../services/intergration.service';
import { FormControl, FormGroup } from '@angular/forms';
import { LoginRequest } from '../../models/login-request';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  constructor(private intergration: IntergrationService) { }

  userForm : FormGroup = new FormGroup({
    username: new FormControl(''),
    password: new FormControl('')
  });

  request: LoginRequest = new LoginRequest;

  doLogin() {
    const formValue = this.userForm.value;
    // console.log(formValue.username)
    // console.log(formValue.password)
    if(formValue.username == '' || formValue.password == '') {
      alert('Empty Credentials');
      return;
    }

    this.request.username = formValue.username;
    this.request.password = formValue.password;

    this.intergration.doLogin(this.request).subscribe({
      next:(res) => {
        console.log("Received response:" + res.result?.token);
      }, error: (err) => {
        console.log("Error received response:" + err);
      }
    });
  }

}
