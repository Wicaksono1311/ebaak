<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Setting extends CI_Controller
{
	public $form_validation;
	public $session;
	public $input;
	public $admin;

	/** Constructor dari Class Setting */
	public function __construct()
	{
		parent::__construct();
		$this->load->model('m_admin', 'admin');
		if ($this->session->userdata('is_login_in') !== TRUE) {
			redirect('Login');
		}
	}

	/** Template untuk memanggil view */
	private function _template($data, $view)
	{
		$this->load->view('view/' . $view, $data);
	}

	/** Menampilkan halaman index Setting */
	public function index()
	{
		$this->form_validation->set_rules(
			'password1',
			'Password',
			'trim|min_length[4]|max_length[16]|required',
			[
				'max_length' => 'Panjang karakter Password maksimal 16 karakter!',
				'min_length' => 'Panjang karakter Password minimal 6 karakter!',
				'required' => 'Password harus diisi!'
			]
		);
		$this->form_validation->set_rules(
			'password2',
			'Password',
			'trim|matches[password1]|required',
			[
				'required' => 'Password harus diisi!',
				'matches' => 'Password tidak sama!'
			]
		);
		$this->form_validation->set_error_delimiters('<div class="alert alert-danger" role="alert">', '</div>');
		if ($this->form_validation->run() === false) {
			$this->session->set_flashdata('pesan', validation_errors());
			$data['title'] = "Dashboard | Ebaak - Layanan BAAK Darmajaya";
			$view = 'v_setting';
			$this->_template($data, $view);
		}else{
			$password = $this->input->post("password1", TRUE);
			$inPass = password_hash($password,PASSWORD_BCRYPT);
			$dataAdmin = [
				'password' => $inPass
			];
			$this->admin->update_admin($dataAdmin);
			$this->session->set_flashdata('pesan2', '<div class="alert alert-success" role="alert">Berhasil Memperbaharui data!</div>');
			redirect('Setting');
		}
	}
}
