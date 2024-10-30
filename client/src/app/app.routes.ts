import { Routes } from '@angular/router';
import { LoginComponent } from './component/login/login.component';
import { AppComponent } from './app.component';

export const routes: Routes = [
    {path: '', redirectTo: 'login', pathMatch: 'full'},
    {path: 'login', component: LoginComponent},
    {path: 'home', component: AppComponent}
];
