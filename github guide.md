Offical URL:  https://guides.github.com/activities/hello-world/

只要读3,4,5步就可以了

精简翻译
<ul>
<li><b>什么是GitHub</b></li>
  GitHub是一个版本控制和协作代码托管平台。它可以让你在任何地方和他人协作。
  这个教程将指导你GitHub最为核心的部分如仓库(repositories),分支(branches),提交(commits)和pull requests。
<li><b>无需编程</b></li>
  做完这个教程只需要一个github账号和网络连接。
  你不需要编程，或使用命令行，或者安装Git(GitHub基于此版本控制软件)。
</ul>
<h1>正文</h1>
<ol>
<li><h1>创建仓库</h1></li>
  仓库用以组织一个工程。仓库可以包含文件夹和文件，如图像、视频、电子表格和数据集————工程所需要的一切都可以。
  我们推荐添加一个README文档，或者一个有关你的工程的信息的文件。GitHub使你创建工程时很容易就可以添加一个README文档。。
  
  你的hello-world仓库可以作为一个存储想法，资源，甚至分享和与他人讨论事务的place。
  
  <ol>
  <li>在GitHub界面右上角，靠近你的头像或身份认证处，点击<b>"+"</b>并在下拉栏中选择<b>"新的仓库"(New repository)</b>.</li>
  <li>命名你的仓库为hello-world.</li>
  <li>写一个简短的描述(description).</li>
  <li>选择<b>初始化仓库并附带README文档(Initialize this repository with a README).</b></li>
  点击<b>创建仓库(Create repository)</b>。
  </ol>
  
<li><h1>创建分支</h1></li>
  <b>建立分支</b>是用以在同一时间存储一个仓库的不同版本的方式。
  默认你的仓库有一个作为最终分支的master分支。我们在将其他分支提交整合到master分支前用其他分支进行实验。
  当你创建一个非master分支的分支时，你应及时做一份master分支在那个时间点的拷贝或截图。
  如果其他人对master分支做了一些改动而你正在处理这个分支时，你可以插入这些更新。
  <img src="https://guides.github.com/activities/hello-world/branching.png" width="1059" height="264" />
  
  你保存过一个文件的不同版本吗?就像这一样:
  <ul>
  <li>story.txt</li>
  <li>story-joe-edit.txt</li>
  <li>story-joe-edit-reviewed.txt</li>
  </ul>
  在GitHub仓库中分支实现相似的目的。
  
  在GitHub，我们的开发者，作家，设计师用分支修复bug并实现特性基于master(产品)分支分工。当一个改动完成了，他们将他们的分支整合到master。
  <ol>
  <li>进入你的新仓库hello-world.</li>
  <li>点击文件列表顶部标签为<b>branch:master</b>的下拉栏.</li>
  <li>在新的分支(branch)文本框中键入分支名(branch name).</li>
  <li>选择下面蓝色的<b>创建分支(Creat branch)</b>栏或者按键盘上的"Enter"键.</li>
  </ol>
  
  现在你有了两个分支，master和readme—edits,他们看上去很相似，但只是一时的。接下来我们要给新分支添加一些改动。
<li><h1>进行并提交修改</h1></li>
  太棒了！现在你的代码视图是readme—edits分支了，此分支是master的一个拷贝。让我们进行一些编辑。
  
  在GitHub中，保存改动被称为提交(commits)。每个提交有其相关联的提交信息(commit message)，即一个解释为什么进行特定改动的描述(description)。提交信息记录你的改动历史，这样其他贡献者便能理解你已经做了什么和为什么这样做。

  <ol>
  <li>点击README.md文件.</li>
  <li>点击文件视图下的右上角的铅笔图标进行编辑.</li>
  <li>在编辑器中写些关于你的事.</li>
  <li>写<b>提交信息(commit message)</b>描述你的改动.</li>
  <li>点击<b>提交改动(Commit changes)</b>按钮.</li>
  </ol>
  
  这个改动只在readme-edits分支的README文件中，所以现在此分支所包含的内容与master不同。
  
<li><h1>打开Pull请求(Pull Request)</h1></li>
  修改得不错！现在，你已经更改了master 主分支上的一个分支(即readme-edits分支)，你可以打开一个Pull请求了.
  Pull请求是GitHub协作工作的核心。
  当你打开一个Pull请求，你给出了你为什么修改的提议，并且希望别人能够针对你的修改作出回复，同步你修改或新增的代码，能够把这些代码合并到他们的分支中。Pull请求能够显示差异以及来自两个分支的内容上的不同。修改和新增的代码用绿色标出，删除的代码用红色标出。
  一旦提交一个改动，就可以打开Pull请求并且开始讨论，直到在代码完成之前（合并到master中，或者最终没有被采纳）。
  
  通过使用GitHub在你的Pull request消息的@mention system，你可以向特定的人或团队征求反馈，不管他们是在大厅下面，还是在10个时区之外。
  你甚至可以在自己的仓库中打开Pull请求，自己合并它们。在开始大型项目之前，这是了解GitHub工作流的一个很好的方法。
  
  <ol>  
  <li>单击 Pull requests 选项，进入“Pull request”页面，点击绿色 New pull request 按钮。</li>
  <li>在Example Comparisons框中，选择你创建的分支readme-edits，与master(原始分支) 进行比较。</li>
  <li>检查并比较你的修改与页面上的差异，确保这些修改是你想要提交的。</li>
  <li>当你满意这些想提交的修改时，点击大的绿色 Create pull request 按钮。</li>
  <li>给你的pull请求写一个标题和一个简短的描述你的修改。</li>
  </ol>
  
<li><h1>合并Pull请求(Merge Pull Request)</h1></li>
  <ol>
  <li>单击绿色 Merge pull request 按钮将修改合并到master分支.</li>
  <li>点击 Confirm merge.</li>
  <li>去前一页删除分支，如果修改已被合并到master， Delete branch 按钮会在紫色框中显示.</li>
  </ol>
</ol>
