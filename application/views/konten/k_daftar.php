<?php
defined('BASEPATH') or exit('No direct script access allowed');?>
<div class="login-box">
	<div class="login-logo">
		<img src="<?= base_url('gambar/darmajaya.png'); ?>" alt="logo Ebaak" width="20%"><br>
		<a href="<?= base_url('Login'); ?>" class="text-white"><b>E</b>Baak</a>
	</div>
	<!-- /.login-logo -->
	<div class="card">
		<div class="card-body login-card-body">
			<p class="login-box-msg">PENDAFTARAN AKUN</p>
			<p class="login-box-msg">
				<?php
				echo $this->session->flashdata('pesan');
				echo $this->session->flashdata('pesan2');
				echo $this->session->flashdata('pesan3');
				?>
			</p>

			<form action="<?= base_url('Daftar'); ?>" method="post">
				<div class="input-group mb-3">
					<select name="jurusan" id="jurusan" class="form-control" required="required">
						<option value="">Pilih</option>
						<?php foreach($jurusan->result_array() as $rowJurusan) :?>
							<option value="<?= sanitasi($rowJurusan['id_jurusan']); ?>">

								<?= ucwords(sanitasi($rowJurusan['nama_jurusan'])); ?></option>
						<?php endforeach; ?>
					</select>
					<div class="input-group-append">
						<div class="input-group-text">
							<span class="fas fa-book"></span>
						</div>
					</div>
				</div>
				<div class="input-group mb-3">
					<input type="text" name="namaLengkap" maxlength="100" class="form-control" value="<?= set_value('namaLengkap'); ?>" placeholder="Nama Lengkap" required="required">
					<div class="input-group-append">
						<div class="input-group-text">
							<span class="fas fa-user"></span>
						</div>
					</div>
				</div>
				<div class="input-group mb-3">
					<input type="email" name="email" maxlength="50" class="form-control" value="<?= set_value('email'); ?>" placeholder="Email" required="required">
					<div class="input-group-append">
						<div class="input-group-text">
							<span class="fas fa-envelope"></span>
						</div>
					</div>
				</div>
				<div class="input-group mb-3">
					<input type="text" name="username" maxlength="30" class="form-control" value="<?= set_value('username'); ?>" placeholder="NPM" required="required">
					<div class="input-group-append">
						<div class="input-group-text">
							<span class="fas fa-id-card"></span>
						</div>
					</div>
				</div>
				<div class="input-group mb-3">
					<input type="password" name="password" maxlength="16" class="form-control" placeholder="Password" required="required">
					<div class="input-group-append">
						<div class="input-group-text">
							<span class="fas fa-lock"></span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="icheck-primary">
							<?= $image; ?>
						</div>
					</div>
					<!-- /.col -->
					<div class="col-md-6">
						<input name="captcha" class="form-control" placeholder="Captcha"/>
					</div>
					<!-- /.col -->
				</div>
				<div class="row mt-4">
					<div class="col-md-12">
						<button type="submit" class="btn btn-primary btn-block">Buat Akun</button>
					</div>
				</div>
			</form>

			<!-- /.social-auth-links -->

			<p class="mb-1 mt-4">
				Sudah Punya Akun ? <a href="<?= base_url('Login'); ?>">Login</a>
			</p>

		</div>
		<!-- /.login-card-body -->
	</div>
</div>
<!-- /.login-box -->
