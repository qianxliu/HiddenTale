/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package qian.xin.library.interfaces;

import androidx.appcompat.app.AppCompatActivity;

/*Fragment的逻辑接口
 * @author Lemon
 * @use implements FragmentPresenter
 * @warn 对象必须是Fragment
 */
public interface FragmentPresenter extends Presenter {

    String ARGUMENT_ID = "ARGUMENT_ID";
    String ARGUMENT_USER_ID = "ARGUMENT_USER_ID";

    int RESULT_OK = AppCompatActivity.RESULT_OK;
    int RESULT_CANCELED = AppCompatActivity.RESULT_CANCELED;
}